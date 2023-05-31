package com.ues.gpo7fb16014.crud.alumno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ues.gpo7fb16014.databinding.ActivityEliminarBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno

class EliminarAlumnoActivity : AppCompatActivity() {
    var binding : ActivityEliminarBinding? = null
    lateinit var controlDB : ControlDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEliminarBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCancelar.setOnClickListener { finish() }
            binding.btnEliminar.setOnClickListener { eliminarAlumno() }
        }
        setContentView(binding!!.root)
    }

    private fun eliminarAlumno() {
        val alumno : Alumno = Alumno()
        binding?.let { binding ->
            alumno.carnet = binding.edtCarnet.text.toString()
            val result = controlDB.eliminar(alumno)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }

    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCarnet.setText("")
        }
    }
}