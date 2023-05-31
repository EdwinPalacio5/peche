package com.ues.gpo7fb16014.crud.alumno

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ues.gpo7fb16014.databinding.ActivityEditarBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno

class EditarAlumnoActivity : AppCompatActivity() {
    var binding : ActivityEditarBinding? = null
    lateinit var controlDB : ControlDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnEditar.setOnClickListener { editarAlumno() }
            binding.btnCancelar.setOnClickListener { finish() }
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

    private fun editarAlumno() {
        val alumno : Alumno = Alumno()
        binding?.let { binding ->
            alumno.carnet = binding.edtCarnet.text.toString()
            alumno.nombre = binding.edtNombre.text.toString()
            alumno.carrera = binding.edtCarrera.text.toString()

            val result = controlDB.editar(alumno)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }


}