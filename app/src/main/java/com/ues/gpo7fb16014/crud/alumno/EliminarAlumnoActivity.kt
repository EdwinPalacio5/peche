package com.ues.gpo7fb16014.crud.alumno

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ues.gpo7fb16014.databinding.ActivityEliminarBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import org.json.JSONObject

class EliminarAlumnoActivity : AppCompatActivity() {
    var binding : ActivityEliminarBinding? = null
    lateinit var controlDB : ControlDB

    private lateinit var requestQueue: RequestQueue
    private val url = "http://192.168.1.13:8000/api/alumnos"

    val alumno : Alumno = Alumno()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEliminarBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        // Obtener el Bundle de la Intent
        val bundle = intent.extras

        // Verificar si el Bundle no es nulo
        if (bundle != null) {
            // Obtener los parámetros del Bundle
            alumno.id = bundle.getInt("id", 0)
            alumno.carnet = bundle.getString("carnet", "")
            alumno.nombre = bundle.getString("nombre", "")
            alumno.carrera = bundle.getString("carrera", "")

        }

        binding?.let { binding ->
            binding.edtCarnet.setText(alumno.carnet)

            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCancelar.setOnClickListener { finish() }
            binding.btnEliminar.setOnClickListener { deleteAlumnoWS(alumno) }
        }
        setContentView(binding!!.root)
    }

    private fun eliminarAlumno() {
        binding?.let { binding ->
            alumno.carnet = binding.edtCarnet.text.toString()
            val result = controlDB.eliminar(alumno)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }

    fun deleteAlumnoWS(alumno : Alumno) {
        requestQueue = Volley.newRequestQueue(this)

        // Crea la solicitud POST utilizando JsonObjectRequest
        val request = StringRequest(
            Request.Method.DELETE, url.plus("/${alumno.id}"),
            { response ->
                // Maneja la respuesta del servidor
                Log.d("POST Response", response.toString())
                showMessageAlert("Eliminación Exitoso", "El alumno ${alumno.nombre} se eliminó exitosamente")
            },
            { error ->
                // Maneja el error de la solicitud
                Log.e("POST Error", error.toString())
                showMessageAlert("Eliminación Fallido", "Ocurrió un error al realizar la eliminación")
            })

        request.apply {
            retryPolicy = DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        }

        requestQueue.add(request)
    }

    fun showMessageAlert(title : String, message : String){
        val builder = AlertDialog.Builder(this)

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

    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCarnet.setText("")
        }
    }
}