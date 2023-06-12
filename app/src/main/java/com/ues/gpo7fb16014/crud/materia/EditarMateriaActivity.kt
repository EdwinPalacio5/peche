package com.ues.gpo7fb16014.crud.materia

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
import com.ues.gpo7fb16014.databinding.ActivityEditarBinding
import com.ues.gpo7fb16014.databinding.ActivityEditarMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Docente
import com.ues.gpo7fb16014.models.Materia
import org.json.JSONObject

class EditarMateriaActivity : AppCompatActivity() {
    var binding : ActivityEditarMateriaBinding? = null
    lateinit var controlDB : ControlDB
    var materia : Materia = Materia()

    private lateinit var requestQueue: RequestQueue
    private val url = "${Constants.url}/api/materias"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarMateriaBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        // Obtener el Bundle de la Intent
        val bundle = intent.extras


        // Verificar si el Bundle no es nulo
        if (bundle != null) {
            // Obtener los parámetros del Bundle
            materia.id = bundle.getInt("id", 0)
            materia.cod_materia = bundle.getString("cod_materia", "")
            materia.nombre = bundle.getString("nombre", "")
            materia.area = bundle.getString("area", "")
        }

        binding?.let { binding ->
            binding.edtCodMateria.setText(materia.cod_materia)
            binding.edtNombre.setText(materia.nombre)
            binding.edtArea.setText(materia.area)

            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnEditar.setOnClickListener { editarMateria() }
            binding.btnCancelar.setOnClickListener { finish() }
        }
        setContentView(binding!!.root)
    }

    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCodMateria.setText("")
            binding.edtNombre.setText("")
            binding.edtArea.setText("")
        }
    }

    private fun editarMateria() {
        //val materia : Materia = Materia()
        binding?.let { binding ->
            materia.cod_materia = binding.edtCodMateria.text.toString()
            materia.nombre = binding.edtNombre.text.toString()
            materia.area = binding.edtArea.text.toString()
            updateMateriaWS(materia);

           /* val result = controlDB.editarMateria(materia)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()*/
        }
    }
    fun updateMateriaWS(materia: Materia ) {
        requestQueue = Volley.newRequestQueue(this)

        val jsonBody = JSONObject()
        jsonBody.put("cod_materia", materia.cod_materia)
        jsonBody.put("nombre", materia.nombre)
        jsonBody.put("area", materia.area)

        // Crea la solicitud POST utilizando JsonObjectRequest
        val request = JsonObjectRequest(
            Request.Method.PUT, url.plus("/${materia.id}"), jsonBody,
            { response ->
                // Maneja la respuesta del servidor
                Log.d("POST Response", response.toString())
                showMessageAlert("Registro Exitoso", "La materia ${materia.nombre} se editó exitosamente", R.raw.success)
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
    }

}