package com.ues.gpo7fb16014.crud.docente

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ues.gpo7fb16014.Constants
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityCrearDocenteBinding
import com.ues.gpo7fb16014.databinding.ActivityCrearMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Docente
import com.ues.gpo7fb16014.models.Materia
import org.json.JSONObject

class CrearDocenteActivity : AppCompatActivity() {
    var binding : ActivityCrearDocenteBinding? = null
    lateinit var controlDB : ControlDB

    private lateinit var requestQueue: RequestQueue
    private val url = "${Constants.url}/api/docentes"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearDocenteBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCrear.setOnClickListener { crearDocente() }
            binding.btnLimpiar.setOnClickListener { limpiarCampos() }
        }
        setContentView(binding!!.root)
    }
    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCodDocente.setText("")
            binding.edtNombre.setText("")
        }
    }

    private fun crearDocente() {
        val docente : Docente = Docente()
        binding?.let { binding ->
            docente.cod_docente = binding.edtCodDocente.text.toString()
            docente.nombre = binding.edtNombre.text.toString()

           /* val result = controlDB.insertarDocente(docente)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()*/
            createDocenteWS(docente)

        }
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

    fun createDocenteWS(docente: Docente ) {
        requestQueue = Volley.newRequestQueue(this)

        val jsonBody = JSONObject()
        jsonBody.put("cod_docente", docente.cod_docente)
        jsonBody.put("nombre", docente.nombre)

        // Crea la solicitud POST utilizando JsonObjectRequest
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                // Maneja la respuesta del servidor
                Log.d("POST Response", response.toString())
                showMessageAlert("Registro Exitoso", "El docente ${docente.nombre} se registro exitosamente", R.raw.success)
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

}