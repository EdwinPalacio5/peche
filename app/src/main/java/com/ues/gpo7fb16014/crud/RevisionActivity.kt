package com.ues.gpo7fb16014.crud

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ues.gpo7fb16014.databinding.ActivityRevisionBinding

class RevisionActivity : AppCompatActivity() {
    var menu = arrayOf("Crear Revision", "Listado de Revisiones")
    var activities = arrayOf("CrearRevisionActivity", "ListarRevisionActivity")
    var binding : ActivityRevisionBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRevisionBinding.inflate(layoutInflater, null, false)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menu)
        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }

            binding.listMenu.adapter = arrayAdapter
            binding.listMenu.setOnItemClickListener { adapterView, view, position, l ->
                val nombreValue = activities[position]
                try {
                    val clase = Class.forName("com.ues.gpo7fb16014.crud.revision.$nombreValue")
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