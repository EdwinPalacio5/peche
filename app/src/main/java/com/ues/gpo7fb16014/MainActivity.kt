package com.ues.gpo7fb16014

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ues.gpo7fb16014.databinding.ActivityMainBinding
import com.ues.gpo7fb16014.db.ControlDB

class MainActivity : AppCompatActivity() {

    var menu = arrayOf("Alumnos", "Materias", "Docentes", "Agregar Evidencias")//"LLENADO DE DATOS"
    var activities = arrayOf("AlumnoActivity", "MateriaActivity", "DocenteActivity", "RevisionActivity")
    var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater, null, false)

        val controlDB : ControlDB = ControlDB(this)

        // access the listView from xml file

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menu)
        binding?.let { binding ->
//            binding.listMenu.adapter = arrayAdapter
//            binding.listMenu.setOnItemClickListener { _, _, position, _ ->
//                if (position < 4) {
//                    val nombreValue = activities[position]
//                    try {
//                        val clase = Class.forName("com.ues.gpo7fb16014.crud.$nombreValue")
//                        val inte = Intent(this, clase)
//                        this.startActivity(inte)
//                    } catch (e: ClassNotFoundException) {
//                        e.printStackTrace()
//                    }
//                }else{
//                    val result = controlDB.llenarBD()
//                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
//                }
//            }
            binding.btnAlumnos.setOnClickListener { launchActivity(activities[0]) }
            binding.btnDocentes.setOnClickListener { launchActivity(activities[1]) }
            binding.btnMaterias.setOnClickListener { launchActivity(activities[2]) }
            binding.btnRevision.setOnClickListener { launchActivity(activities[3])  }
        }

        setContentView(binding!!.root)
    }

    fun launchActivity(nombreValue : String){
        try {
            val clase = Class.forName("com.ues.gpo7fb16014.crud.$nombreValue")
            val inte = Intent(this, clase)
            this.startActivity(inte)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}