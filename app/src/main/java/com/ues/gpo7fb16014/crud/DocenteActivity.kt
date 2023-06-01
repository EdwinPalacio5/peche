package com.ues.gpo7fb16014.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityDocenteBinding
import com.ues.gpo7fb16014.databinding.ActivityMateriaBinding

class DocenteActivity : AppCompatActivity() {
    var menu = arrayOf("Crear Docente", "Listar Docentes", "Editar Docente", "Eliminar Docente")
    var activities = arrayOf("CrearDocenteActivity", "ListarDocenteActivity", "EditarDocenteActivity", "EliminarDocenteActivity")
    var binding : ActivityDocenteBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDocenteBinding.inflate(layoutInflater, null, false)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menu)
        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }

            binding.listMenu.adapter = arrayAdapter
            binding.listMenu.setOnItemClickListener { adapterView, view, position, l ->
                val nombreValue = activities[position]
                try {
                    val clase = Class.forName("com.ues.gpo7fb16014.crud.docente.$nombreValue")
                    val inte = Intent(this, clase)
                    this.startActivity(inte)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            }
        }


        setContentView(binding!!.root)
    }
}