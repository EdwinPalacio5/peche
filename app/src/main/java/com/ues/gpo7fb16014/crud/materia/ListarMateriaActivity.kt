package com.ues.gpo7fb16014.crud.materia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.ues.gpo7fb16014.Constants
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.crud.alumno.AlumnoListAdapter
import com.ues.gpo7fb16014.databinding.ActivityListarAlumnoBinding
import com.ues.gpo7fb16014.databinding.ActivityListarMateriaBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import com.ues.gpo7fb16014.models.Docente
import com.ues.gpo7fb16014.models.Materia
import org.json.JSONArray
import org.json.JSONObject

class ListarMateriaActivity : AppCompatActivity() {
    var binding : ActivityListarMateriaBinding? = null
    lateinit var controlDB : ControlDB

    var items : ArrayList<Materia> = ArrayList()
    lateinit  var adapter : MateriaListAdapter

    private lateinit var requestQueue: RequestQueue
    private val url = "${Constants.url}/api/materias"

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

    override fun onStart() {
        super.onStart()
        requestListMaterias()
    }

    fun requestListMaterias() {
        requestQueue = Volley.newRequestQueue(this)
        val getRequest = StringRequest(Request.Method.GET, url,
            { response ->
                println(response)
                val gson = GsonBuilder().create()
                items = ArrayList()
                val resultArray = JSONArray(response)
                for (x in 0 until resultArray.length()) {
                    val jsonObject : JSONObject = resultArray.getJSONObject(x)
                    val item: Materia = gson.fromJson(jsonObject.toString(), Materia::class.java)
                    items.add(item)
                }
                renderMaterias()
                binding?.let { binding ->
                    binding.loading.visibility = View.GONE
                }
            },
            { error ->
                println(error)
                binding?.let {
                    it.loading.visibility = View.GONE
                }
            }).apply {
            retryPolicy = DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        }

        requestQueue.add(getRequest)
    }


    private fun renderMaterias() {
        //items = controlDB.getAllMaterias()
        adapter = MateriaListAdapter(this, items)

        binding?.let { binding ->
            binding.list.adapter = adapter
            binding.list.layoutManager = LinearLayoutManager(this)
        }
    }
}