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

        takePhoto()
        setContentView(binding!!.root)
    }

    private fun takePhoto() {
        val fm: FragmentManager = supportFragmentManager
        val ft = fm.beginTransaction()
        val takePhotoFragment = CamaraFragment()
        binding?.fragmentContainer?.let { ft.add(it.id, takePhotoFragment, "TAKE_PHOT0") }
        ft.setReorderingAllowed(true)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
        fm.setFragmentResultListener("take_photo", this) { requestKey: String?, bundle: Bundle? ->
            bundle?.let {
                println(it.getString("filename"))
            }
        }
    }
}
