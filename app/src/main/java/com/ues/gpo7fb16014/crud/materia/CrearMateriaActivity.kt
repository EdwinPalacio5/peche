package com.ues.gpo7fb16014.crud.materia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.crud.MateriaActivity
import com.ues.gpo7fb16014.databinding.ActivityCrearAlumnoBinding
import com.ues.gpo7fb16014.databinding.ActivityCrearMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Materia

class CrearMateriaActivity : AppCompatActivity() {
    var binding : ActivityCrearMateriaBinding? = null
    lateinit var controlDB : ControlDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearMateriaBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCrear.setOnClickListener { crearMateria() }
            binding.btnLimpiar.setOnClickListener { limpiarCampos() }
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

    private fun crearMateria() {
        val materia : Materia = Materia()
        binding?.let { binding ->
            materia.cod_materia = binding.edtCodMateria.text.toString()
            materia.nombre = binding.edtNombre.text.toString()
            materia.area = binding.edtArea.text.toString()

            val result = controlDB.insertarMateria(materia)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }
}