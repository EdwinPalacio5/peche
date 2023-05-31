package com.ues.gpo7fb16014.crud.alumno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ues.gpo7fb16014.R
import com.ues.gpo7fb16014.databinding.ActivityCrearAlumnoBinding

class CrearAlumnoActivity : AppCompatActivity() {
    var binding : ActivityCrearAlumnoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearAlumnoBinding.inflate(layoutInflater, null, false)

        setContentView(binding!!.root)
    }
}