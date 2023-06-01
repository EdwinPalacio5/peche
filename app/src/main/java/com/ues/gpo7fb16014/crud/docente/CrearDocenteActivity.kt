package com.ues.gpo7fb16014.crud.docente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityCrearDocenteBinding
import com.ues.gpo7fb16014.databinding.ActivityCrearMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Docente
import com.ues.gpo7fb16014.models.Materia

class CrearDocenteActivity : AppCompatActivity() {
    var binding : ActivityCrearDocenteBinding? = null
    lateinit var controlDB : ControlDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearDocenteBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
            binding.btnCrear.setOnClickListener { crearDocente() }
            binding.btnLimpiar.setOnClickListener { limpiarCampos() }
        }
        setContentView(binding!!.root)
    }
    private fun limpiarCampos() {
        binding?.let { binding ->
            binding.edtCodDocente.setText("")
            binding.edtNombre.setText("")
        }
    }

    private fun crearDocente() {
        val docente : Docente = Docente()
        binding?.let { binding ->
            docente.cod_docente = binding.edtCodDocente.text.toString()
            docente.nombre = binding.edtNombre.text.toString()

            val result = controlDB.insertarDocente(docente)
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }
    }
}