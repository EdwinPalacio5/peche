package com.ues.gpo7fb16014.crud.materia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.crud.alumno.AlumnoListAdapter
import com.ues.gpo7fb16014.databinding.ActivityListarAlumnoBinding
import com.ues.gpo7fb16014.databinding.ActivityListarMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Materia

class ListarMateriaActivity : AppCompatActivity() {
    var binding : ActivityListarMateriaBinding? = null
    lateinit var controlDB : ControlDB

    var items : ArrayList<Materia> = ArrayList()
    lateinit  var adapter : MateriaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListarMateriaBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
        }

        renderMaterias()
        setContentView(binding!!.root)
    }

    private fun renderMaterias() {
        items = controlDB.getAllMaterias()
        adapter = MateriaListAdapter(this, items)

        binding?.let { binding ->
            binding.list.adapter = adapter
            binding.list.layoutManager = LinearLayoutManager(this)
        }
    }
}