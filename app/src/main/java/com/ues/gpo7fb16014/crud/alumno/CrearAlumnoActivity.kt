package com.ues.gpo7fb16014.crud.alumno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ues.gpo7fb16014.databinding.ActivityCrearAlumnoBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno

class CrearAlumnoActivity : AppCompatActivity() {
    var binding : ActivityCrearAlumnoBinding? = null
    lateinit var controlDB : ControlDB

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

            val result = controlDB.insertarAlumno(alumno)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }
}