package com.ues.gpo7fb16014.crud.materia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityEditarBinding
import com.ues.gpo7fb16014.databinding.ActivityEditarMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Materia

class EditarMateriaActivity : AppCompatActivity() {
    var binding : ActivityEditarMateriaBinding? = null
    lateinit var controlDB : ControlDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarMateriaBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
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
        val materia : Materia = Materia()
        binding?.let { binding ->
            materia.cod_materia = binding.edtCodMateria.text.toString()
            materia.nombre = binding.edtNombre.text.toString()
            materia.area = binding.edtArea.text.toString()

            val result = controlDB.editarMateria(materia)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }
}