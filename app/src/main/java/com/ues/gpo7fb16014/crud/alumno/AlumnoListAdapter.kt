package com.ues.gpo7fb16014.crud.alumno

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ues.gpo7fb16014.databinding.AlumnoItemBinding
import com.ues.gpo7fb16014.models.Alumno

class AlumnoListAdapter(
    var context: Context,
    var items : ArrayList<Alumno> = ArrayList())
    : RecyclerView.Adapter<AlumnoListAdapter.ViewHolder>(){


    class ViewHolder(view: AlumnoItemBinding) : RecyclerView.ViewHolder(view.root) {
        val binding: AlumnoItemBinding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AlumnoItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            tvCarnet.text = item.carnet
            tvNombre.text = item.nombre
            tvCarrera.text = item.carrera
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}