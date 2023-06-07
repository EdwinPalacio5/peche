package com.ues.gpo7fb16014.crud.alumno

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.ues.gpo7fb16014.Constants
import com.ues.gpo7fb16014.databinding.ActivityListarAlumnoBinding
import com.ues.gpo7fb16014.db.ControlDB
import com.ues.gpo7fb16014.models.Alumno
import org.json.JSONArray
import org.json.JSONObject

class ListarAlumnoActivity : AppCompatActivity() {
    var binding : ActivityListarAlumnoBinding? = null
    lateinit var controlDB : ControlDB

    var items : ArrayList<Alumno> = ArrayList()
    lateinit  var adapter : AlumnoListAdapter

    private lateinit var requestQueue: RequestQueue
    private val url = "${Constants.url}/api/alumnos"

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

    override fun onStart() {
        super.onStart()
        requestListAlumnos()
    }

    private fun renderAlumnos() {
        //items = controlDB.getAllAlumnos()
        adapter = AlumnoListAdapter(this, items)

        binding?.let { binding ->
            binding.list.adapter = adapter
            binding.list.layoutManager = LinearLayoutManager(this)
        }
    }

    fun requestListAlumnos() {
        requestQueue = Volley.newRequestQueue(this)

        val getRequest = StringRequest(Request.Method.GET, url,
            { response ->
                println(response)
                val gson = GsonBuilder().create()
                items = ArrayList()
                val resultArray = JSONArray(response)
                for (x in 0 until resultArray.length()) {
                    val jsonObject : JSONObject = resultArray.getJSONObject(x)
                    val item: Alumno = gson.fromJson(jsonObject.toString(), Alumno::class.java)
                    items.add(item)
                }
                renderAlumnos()
                binding?.let {
                    it.loading.visibility = View.GONE
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
}