package com.ues.gpo7fb16014.crud.docente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.crud.materia.MateriaListAdapter
import com.ues.gpo7fb16014.databinding.ActivityListarDocenteBinding
import com.ues.gpo7fb16014.databinding.ActivityListarMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Docente
import com.ues.gpo7fb16014.models.Materia

class ListarDocenteActivity : AppCompatActivity() {
    var binding : ActivityListarDocenteBinding? = null
    lateinit var controlDB : ControlDB

    var items : ArrayList<Docente> = ArrayList()
    lateinit  var adapter : DocenteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListarDocenteBinding.inflate(layoutInflater, null, false)
        controlDB = ControlDB(this)

        binding?.let { binding ->
            binding.toolbar.setNavigationOnClickListener { finish() }
        }

        renderDocentes()
        setContentView(binding!!.root)
    }

    private fun renderDocentes() {
        items = controlDB.getAllDocentes()
        adapter = DocenteListAdapter(this, items)

        binding?.let { binding ->
            binding.list.adapter = adapter
            binding.list.layoutManager = LinearLayoutManager(this)
        }
    }
}