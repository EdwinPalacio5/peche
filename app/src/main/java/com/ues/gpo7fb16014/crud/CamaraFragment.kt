package com.ues.gpo7fb16014.crud

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.ImageCapture.OutputFileResults
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ues.gpo7fb16014.databinding.FragmentCamaraBinding
import com.ues.gpo7fb16014.rotate
import com.ues.gpo7fb16014.toBitmap
import java.io.File
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CamaraFragment : Fragment() {
    var binding: FragmentCamaraBinding? = null

    private lateinit var requiredPermissions: Array<String>
    private var camera: Camera? = null
    var path: String = ""
    private var previewNewPhoto: Bitmap? = null

    //Parameters
    private var cFileName: String = ""
    private val executor: Executor = Executors.newSingleThreadExecutor()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCamaraBinding.inflate(inflater, container, false)
        val permissions = ArrayList<String>()
        permissions.add(Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        path = requireActivity().filesDir.path

        requiredPermissions = permissions.toTypedArray()
        checkPermissions()
        binding!!.clearPic.setOnClickListener { clearPhoto() }
        binding!!.closeNewPic.setOnClickListener { actionBack() }

        showCamera()
        return binding!!.root
    }

    private fun showCamera() {
        binding!!.layoutNewPhoto.visibility = View.VISIBLE
        clearControls()
    }

    private fun checkPermissions() {
        if (allPermissionsGranted()) {
            startCameraPicture()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), requiredPermissions, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun allPermissionsGranted() = requiredPermissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCameraPicture()
                binding!!.takePicture.isEnabled = true
            } else {
                binding!!.takePicture.isEnabled = false
                Toast.makeText(activity, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //endregion
    private fun startCameraPicture() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider)
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        val imageAnalysis = ImageAnalysis.Builder().setTargetRotation(Surface.ROTATION_0)
            .setTargetResolution(Size(1024, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        val builder = ImageCapture.Builder().setTargetRotation(Surface.ROTATION_0)
        val imageCapture = builder.setTargetRotation(Surface.ROTATION_0)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
        binding!!.takePicture.setOnClickListener { actionTakePhoto(imageCapture) }
        binding!!.savePic.setOnClickListener {
            actionSavePhoto()
        }
        if (camera != null) cameraProvider.unbindAll()
        try {
            camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )
            preview.setSurfaceProvider(binding!!.cameraPreview.surfaceProvider)
            setEnableControls(true, binding!!.takePicture)
        } catch (e: Exception) {
            println("USE CASE FAILED : " + e.message)
        }
    }

    private fun actionBack() {
        val fm = parentFragmentManager
        val ft = fm.beginTransaction()
        ft.remove(this)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        ft.commit()

        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun clearPhoto() {
        removeFile(path, cFileName)
        clearControls()
    }

    private fun clearControls() {
        cFileName = ""
        setEnableControls(false, binding!!.clearPic)
        setEnableControls(false, binding!!.savePic)
        setEnableControls(true, binding!!.takePicture)
        startCameraPicture()
        binding!!.cameraPreview.visibility = View.VISIBLE
        binding!!.picPreview.visibility = View.GONE
        binding!!.picPreview.setImageBitmap(null)
        previewNewPhoto = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun actionSavePhoto() {
        val result = Bundle()
        result.putString("filename", cFileName)
        parentFragmentManager.setFragmentResult("take_photo", result)
        actionBack()
    }

    private fun actionTakePhoto(imageCapture: ImageCapture) {
        binding!!.loadingView.visibility = View.VISIBLE
        imageCapture.takePicture(executor, object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                val degValues = image.imageInfo.rotationDegrees.toDouble()
                requireActivity().runOnUiThread {
                    previewNewPhoto = image.toBitmap()
                    previewNewPhoto = previewNewPhoto!!.rotate(degValues)
                    binding!!.picPreview.setImageBitmap(previewNewPhoto)
                    binding!!.picPreview.visibility = View.VISIBLE
                    binding!!.cameraPreview.visibility = View.GONE
                    setEnableControls(false, binding!!.takePicture)
                    setEnableControls(true, binding!!.savePic)
                    setEnableControls(true, binding!!.clearPic)
                    saveFile(imageCapture)
                }
            }
        })
    }

    private fun saveFile(imageCapture: ImageCapture) {
        val date = Date().time.toString()
        cFileName = String.format(Locale.getDefault(), "%s.jpg", date)
        val file = File(path, cFileName)
        val outputFileOptions = OutputFileOptions.Builder(file).build()
        imageCapture.takePicture(
            outputFileOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: OutputFileResults) {
                    println("IMAGE SAVED  ")
                }

                override fun onError(error: ImageCaptureException) {
                    println("IMAGE NOT SAVED  ")
                    error.printStackTrace()
                }
            })
        binding!!.loadingView.visibility = View.GONE
    }

    private fun removeFile(path: String, fileName: String) {
        val file = File(path, fileName)
        if (file.exists()) {
            System.out.printf(
                Locale.getDefault(),
                "DELETED FILE %s Result: %s%n",
                fileName,
                file.delete()
            )
        } else {
            println("FILE DOEST EXISTS")
        }
    }

    private fun setEnableControls(enable: Boolean, button: Button) {
        val alpha = if (enable) 1.0f else 0.3f
        button.isEnabled = enable
        button.alpha = alpha
    }

    override fun onDestroy() {
        super.onDestroy()
        previewNewPhoto = null
        camera = null
    }

    companion object {
        //Take photo
        private const val REQUEST_CODE_PERMISSIONS = 1001
    }
}