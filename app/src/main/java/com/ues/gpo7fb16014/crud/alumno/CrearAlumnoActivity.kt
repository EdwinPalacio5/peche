package com.ues.gpo7fb16014.crud.alumno

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ues.gpo7fb16014.Constants
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityCrearAlumnoBinding
import com.ues.gpo7fb16014.databinding.AlumnoItemBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import org.json.JSONObject

class CrearAlumnoActivity : AppCompatActivity() {
    var binding : ActivityCrearAlumnoBinding? = null
    lateinit var controlDB : ControlDB

    private lateinit var requestQueue: RequestQueue
    private val url = "${Constants.url}/api/alumnos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearAlumnoBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCrear.setOnClickListener { crearAlumno() }
            binding.btnLimpiar.setOnClickListener { limpiarCampos() }
        }
        setContentView(binding!!.root)
    }

    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCarnet.setText("")
            binding.edtNombre.setText("")
            binding.edtCarrera.setText("")
        }
    }

    private fun crearAlumno() {
        val alumno : Alumno = Alumno()
        binding?.let { binding ->
            alumno.carnet = binding.edtCarnet.text.toString()
            alumno.nombre = binding.edtNombre.text.toString()
            alumno.carrera = binding.edtCarrera.text.toString()

//            val result = controlDB.insertarAlumno(alumno)
//            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
//            limpiarCampos()
            createAlumnoWS(alumno)
        }
    }

    fun createAlumnoWS(alumno : Alumno) {
        requestQueue = Volley.newRequestQueue(this)

        val jsonBody = JSONObject()
        jsonBody.put("carnet", alumno.carnet)
        jsonBody.put("nombre", alumno.nombre)
        jsonBody.put("carrera", alumno.carrera)

        // Crea la solicitud POST utilizando JsonObjectRequest
        val request = JsonObjectRequest(Request.Method.POST, url, jsonBody,
            { response ->
                // Maneja la respuesta del servidor
                Log.d("POST Response", response.toString())
                showMessageAlert("Registro Exitoso", "El alumno ${alumno.nombre} se registro exitosamente", R.raw.success)
            },
            { error ->
                // Maneja el error de la solicitud
                Log.e("POST Error", error.toString())
                showMessageAlert("Registro Fallido", "Ocurrió un error al realizar el registro", R.raw.failed)
            })

        request.apply {
            retryPolicy = DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        }

        requestQueue.add(request)
    }

    fun showMessageAlert(title : String, message : String, animation : Int){
        val builder = AlertDialog.Builder(this)

        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_layout, null)
        val lottieAnimationView = dialogView.findViewById<LottieAnimationView>(R.id.lottieAnimationView)

        // Set up the Lottie animation
        lottieAnimationView.setAnimation(animation)
        lottieAnimationView.playAnimation()
        lottieAnimationView.loop(false)

        builder.setView(dialogView)
        // Set the dialog title and message
        builder.setTitle(title)
            .setMessage(message)

        // Set the positive button with its click listener
        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            // Handle the button click event
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the dialog
        val alertDialog = builder.create()
        alertDialog.show()
        limpiarCampos()
    }
}