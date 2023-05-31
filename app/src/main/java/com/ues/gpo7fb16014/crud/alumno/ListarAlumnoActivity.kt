package com.ues.gpo7fb16014.crud.alumno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ues.gpo7fb16014.databinding.ActivityListarAlumnoBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno

class ListarAlumnoActivity : AppCompatActivity() {
    var binding : ActivityListarAlumnoBinding? = null
    lateinit var controlDB : ControlDB

    var items : ArrayList<Alumno> = ArrayList()
    lateinit  var adapter : AlumnoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarAlumnoBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
        }

        renderAlumnos()
        setContentView(binding!!.root)
    }

    private fun renderAlumnos() {
        items = controlDB.getAllAlumnos()
        adapter = AlumnoListAdapter(this, items)

        binding?.let { binding ->
            binding.list.adapter = adapter
            binding.list.layoutManager = LinearLayoutManager(this)
        }
    }
}