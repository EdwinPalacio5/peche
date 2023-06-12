package com.ues.gpo7fb16014.crud.revision

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.ues.gpo7fb16014.Constants
import com.ues.gpo7fb16014.crud.CamaraFragment
import com.ues.gpo7fb16014.databinding.ActivityCrearRevisionBinding
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Revision
import com.ues.gpo7fb16014.rotate
import com.ues.gpo7fb16014.toBase64
import org.json.JSONObject
import java.io.File
import java.util.Locale

class CrearRevisionActivity : AppCompatActivity() {
    var binding : ActivityCrearRevisionBinding? = null

    var revision : Revision = Revision()
    var alumnos : ArrayList<Alumno> = ArrayList()
    //locales, motivos

    private lateinit var requestQueue: RequestQueue
    private val url = "${Constants.url}/api/revisiones"
    private val urlData = "${Constants.url}/api/data-revision"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearRevisionBinding.inflate(layoutInflater, null, false)

        binding?.let {binding ->
            binding.btnAgregarFoto.setOnClickListener { takePhoto() }
        }

        requestData()

        setContentView(binding!!.root)
    }

    private fun requestData() {

        requestQueue = Volley.newRequestQueue(this)

        val getRequest = StringRequest(Request.Method.GET, urlData,
            { response ->
                println(response)
                val gson = GsonBuilder().create()
                //inicializar
                alumnos = ArrayList()


                val result = JSONObject(response)
                val alumnosArray = result.getJSONArray("alumnos")
                for (x in 0 until alumnosArray.length()) {
                    val jsonObject : JSONObject = alumnosArray.getJSONObject(x)
                    val item: Alumno = gson.fromJson(jsonObject.toString(), Alumno::class.java)
                    alumnos.add(item)
                }
                renderAlumnos()
                binding?.let {
                    it.loading.visibility = View.GONE
                }
            },
            { error ->
                println(error)
                binding?.let {
                    it.loading.visibility = View.GONE
                }
            }).apply {
            retryPolicy = DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        }

        requestQueue.add(getRequest)
    }


    private fun renderAlumnos() {
        val adapter: ArrayAdapter<Alumno> = object : ArrayAdapter<Alumno>(this, android.R.layout.simple_spinner_item, alumnos) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                (v as TextView).textSize = 14f
                v.text = alumnos[position].nombre

                v.post {
                    v.isSingleLine = false
                    v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                return v
            }
        }

        binding?.let { binding ->
            binding.localSpinner.setAdapter(adapter)
            binding.localSpinner.setText("Select", false)
            binding.localSpinner.setOnItemClickListener { parent, view, position, id ->
                revision.alumno_id = alumnos[position].id
                revision.carnet = alumnos[position].carnet
            }
        }

    }

    private fun takePhoto() {
        val fm: FragmentManager = supportFragmentManager
        val ft = fm.beginTransaction()
        val takePhotoFragment = CamaraFragment()
        binding?.layoutMain?.let { ft.add(it.id, takePhotoFragment, "TAKE_PHOT0") }
        ft.setReorderingAllowed(true)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
        fm.setFragmentResultListener("take_photo", this) { requestKey: String?, bundle: Bundle? ->
            bundle?.let {
                revision.filename = it.getString("filename", revision.filename)
                renderFoto(revision.filename)
                println(revision.filename)
                println(getBase64(filename = revision.filename))
            }
        }
    }

    private fun renderFoto(filename: String) {
        binding?.let { binding ->
            val path : String = filesDir.path
            if (filename.isNotEmpty()) {
                val imgFile: File = File(path, filename)
                if (imgFile.exists()) {
                    Glide.with(this)
                        .load(Uri.fromFile(imgFile))
                        .into(binding.imageRevision)
                }
            }
        }
    }

    fun getBase64(filename : String) : String{
        var base64 = ""
        try {
            val path : String = filesDir.path
            val file = File(path, filename)
            if (file.exists()) {
                val bmOptions = BitmapFactory.Options()
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, bmOptions)
                base64 = bitmap.toBase64()// get base64 encoded string.
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return base64
    }
}