package com.ues.gpo7fb16014.crud.docente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ues.gpo7fb16014.databinding.ActivityEliminarDocenteBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Docente

class EliminarDocenteActivity : AppCompatActivity() {
    var binding : ActivityEliminarDocenteBinding? = null
    lateinit var controlDB : ControlDB
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityEliminarDocenteBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCancelar.setOnClickListener { finish() }
            binding.btnEliminar.setOnClickListener { eliminarDocente() }
        }
        setContentView(binding!!.root)
    }

    private fun eliminarDocente() {
        val docente : Docente = Docente()
        binding?.let { binding ->
            docente.cod_docente = binding.edtCodDocente.text.toString()
            val result = controlDB.eliminarDocente(docente)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }

    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCodDocente.setText("")
        }
    }
}