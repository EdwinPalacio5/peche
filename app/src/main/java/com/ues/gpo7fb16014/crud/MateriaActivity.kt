package com.ues.gpo7fb16014.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.ues.gpo7fb16014.databinding.ActivityMateriaBinding

class MateriaActivity : AppCompatActivity() {
    var menu = arrayOf("Crear Materia", "Listar Materia", "Editar Materia", "Eliminar Materia")
    var activities = arrayOf("CrearMateriaActivity", "ListarMateriaActivity", "EditarMateriaActivity", "EliminarMateriaActivity")
    var binding : ActivityMateriaBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMateriaBinding.inflate(layoutInflater, null, false)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menu)
        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }

            binding.listMenu.adapter = arrayAdapter
            binding.listMenu.setOnItemClickListener { adapterView, view, position, l ->
                val nombreValue = activities[position]
                try {
                    val clase = Class.forName("com.ues.gpo7fb16014.crud.materia.$nombreValue")
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