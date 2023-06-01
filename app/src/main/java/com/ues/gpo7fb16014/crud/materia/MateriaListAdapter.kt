package com.ues.gpo7fb16014.crud.materia

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ues.gpo7fb16014.crud.alumno.AlumnoListAdapter
import com.ues.gpo7fb16014.databinding.AlumnoItemBinding
import com.ues.gpo7fb16014.databinding.MateriaItemBinding
import com.ues.gpo7fb16014.models.Materia

class MateriaListAdapter(var context: Context, var items : ArrayList<Materia> = ArrayList())
    : RecyclerView.Adapter<MateriaListAdapter.ViewHolder>()
{
    class ViewHolder(view: MateriaItemBinding) : RecyclerView.ViewHolder(view.root) {
        val binding: MateriaItemBinding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriaListAdapter.ViewHolder {
        val binding = MateriaItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MateriaListAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            tvCodigo.text = item.cod_materia
            tvNombre.text = item.nombre
            tvArea.text = item.area
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}