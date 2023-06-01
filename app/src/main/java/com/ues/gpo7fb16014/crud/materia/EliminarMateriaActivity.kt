package com.ues.gpo7fb16014.crud.materia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityEliminarBinding
import com.ues.gpo7fb16014.databinding.ActivityEliminarMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Materia

class EliminarMateriaActivity : AppCompatActivity() {
    var binding : ActivityEliminarMateriaBinding? = null
    lateinit var controlDB : ControlDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEliminarMateriaBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCancelar.setOnClickListener { finish() }
            binding.btnEliminar.setOnClickListener { eliminarMateria() }
        }
        setContentView(binding!!.root)
    }

    private fun eliminarMateria() {
        val materia : Materia = Materia()
        binding?.let { binding ->
            materia.cod_materia = binding.edtCodMateria.text.toString()
            val result = controlDB.eliminarMateria(materia)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }

    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCodMateria.setText("")
        }
    }
}