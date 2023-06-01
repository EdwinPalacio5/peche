package com.ues.gpo7fb16014.crud.docente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityEditarDocenteBinding
import com.ues.gpo7fb16014.databinding.ActivityEditarMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Docente
import com.ues.gpo7fb16014.models.Materia

class EditarDocenteActivity : AppCompatActivity() {
    var binding : ActivityEditarDocenteBinding? = null
    lateinit var controlDB : ControlDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarDocenteBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnEditar.setOnClickListener { editarDocente() }
            binding.btnCancelar.setOnClickListener { finish() }
        }
        setContentView(binding!!.root)
    }

    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCodDocente.setText("")
            binding.edtNombre.setText("")
        }
    }

    private fun editarDocente() {
        val docente : Docente = Docente()
        binding?.let { binding ->
            docente.cod_docente = binding.edtCodDocente.text.toString()
            docente.nombre = binding.edtNombre.text.toString()

            val result = controlDB.editarDocente(docente)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }
}