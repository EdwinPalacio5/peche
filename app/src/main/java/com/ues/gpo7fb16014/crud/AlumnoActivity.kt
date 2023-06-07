package com.ues.gpo7fb16014.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityAlumnoBinding
import com.ues.gpo7fb16014.db.ControlDB

class AlumnoActivity : AppCompatActivity() {

    var menu = arrayOf("Crear Alumno", "Listar Alumnos")//"Editar Alumno", "Eliminar Alumno"
    var activities = arrayOf("CrearAlumnoActivity", "ListarAlumnoActivity")//"EditarAlumnoActivity", "EliminarAlumnoActivity"
    var binding : ActivityAlumnoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlumnoBinding.inflate(layoutInflater, null, false)

        // access the listView from xml file

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menu)
        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }

            binding.listMenu.adapter = arrayAdapter
            binding.listMenu.setOnItemClickListener { adapterView, view, position, l ->
                val nombreValue = activities[position]
                try {
                    val clase = Class.forName("com.ues.gpo7fb16014.crud.alumno.$nombreValue")
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