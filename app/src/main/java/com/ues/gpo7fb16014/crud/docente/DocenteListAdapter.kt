package com.ues.gpo7fb16014.crud.docente

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ues.gpo7fb16014.crud.materia.MateriaListAdapter
import com.ues.gpo7fb16014.databinding.DocenteItemBinding
import com.ues.gpo7fb16014.databinding.MateriaItemBinding
import com.ues.gpo7fb16014.models.Docente
import com.ues.gpo7fb16014.models.Materia

class DocenteListAdapter(var context: Context, var items : ArrayList<Docente> = ArrayList())
    : RecyclerView.Adapter<DocenteListAdapter.ViewHolder>()

{
    class ViewHolder(view: DocenteItemBinding) : RecyclerView.ViewHolder(view.root) {
        val binding: DocenteItemBinding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocenteListAdapter.ViewHolder {
        val binding = DocenteItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocenteListAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            tvCodDocente.text = item.cod_docente
            tvNombre.text = item.nombre
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}