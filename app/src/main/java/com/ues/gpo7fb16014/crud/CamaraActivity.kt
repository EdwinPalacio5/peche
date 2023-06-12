package com.ues.gpo7fb16014.crud

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ues.gpo7fb16014.databinding.ActivityCamaraBinding

class CamaraActivity : AppCompatActivity() {

    var binding : ActivityCamaraBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCamaraBinding.inflate(layoutInflater, null, false)


        setContentView(binding!!.root)
    }


}
