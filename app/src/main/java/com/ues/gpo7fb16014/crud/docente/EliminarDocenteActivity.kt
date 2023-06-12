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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ues.gpo7fb16014.Constants
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityEliminarDocenteBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Docente

class EliminarDocenteActivity : AppCompatActivity() {
    var binding : ActivityEliminarDocenteBinding? = null
    lateinit var controlDB : ControlDB

    private lateinit var requestQueue: RequestQueue
    private val url = "${Constants.url}/api/docentes"
    val docente : Docente = Docente()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityEliminarDocenteBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        val bundle = intent.extras

        // Verificar si el Bundle no es nulo
        if (bundle != null) {
            // Obtener los parámetros del Bundle
            docente.id = bundle.getInt("id", 0)
            docente.cod_docente = bundle.getString("cod_docente", "")
            docente.nombre = bundle.getString("nombre", "")

        }


        binding?.let { binding ->
            binding.edtCodDocente.setText(docente.cod_docente)
            binding.edtCodDocente.isEnabled = false
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCancelar.setOnClickListener { finish() }
            binding.btnEliminar.setOnClickListener { deleteDocenteWS(docente) }
        }
        setContentView(binding!!.root)
    }

    private fun eliminarDocente() {
        binding?.let { binding ->
            docente.cod_docente = binding.edtCodDocente.text.toString()
            val result = controlDB.eliminarDocente(docente)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
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
            finish()
        }

        // Create and show the dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun deleteDocenteWS(docente : Docente) {
        requestQueue = Volley.newRequestQueue(this)

        // Crea la solicitud POST utilizando JsonObjectRequest
        val request = StringRequest(
            Request.Method.DELETE, url.plus("/${docente.id}"),
            { response ->
                // Maneja la respuesta del servidor
                Log.d("POST Response", response.toString())
                showMessageAlert("Eliminación Exitoso", "El alumno ${docente.nombre} se eliminó exitosamente", R.raw.success)
            },
            { error ->
                // Maneja el error de la solicitud
                Log.e("POST Error", error.toString())
                showMessageAlert("Eliminación Fallido", "Ocurrió un error al realizar la eliminación", R.raw.failed)
            })

        request.apply {
            retryPolicy = DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        }

        requestQueue.add(request)
    }


    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCodDocente.setText("")
        }
    }
}