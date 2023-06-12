package com.ues.gpo7fb16014.crud.docente

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ues.gpo7fb16014.crud.alumno.EditarAlumnoActivity
import com.ues.gpo7fb16014.crud.alumno.EliminarAlumnoActivity
import com.ues.gpo7fb16014.crud.materia.MateriaListAdapter
import com.ues.gpo7fb16014.databinding.DocenteItemBinding
import com.ues.gpo7fb16014.databinding.MateriaItemBinding
import com.ues.gpo7fb16014.models.Alumno
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

            btnEditar.setOnClickListener {
                startActivityEditar(item)
            }

            btnEliminar.setOnClickListener {
                startActivityEliminar(item)
            }
        }




    }

    private fun startActivityEditar(item: Docente) {
        // Crear el Bundle
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("cod_docente", item.cod_docente)
        bundle.putString("nombre", item.nombre)

        // Crear el Intent y agregar el Bundle
        val intent = Intent(context, EditarDocenteActivity::class.java)
        intent.putExtras(bundle)

        // Iniciar la segunda Activity
        context.startActivity(intent)
    }

    private fun startActivityEliminar(item: Docente) {
        // Crear el Bundle
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("cod_docente", item.cod_docente)
        bundle.putString("nombre", item.nombre)

        // Crear el Intent y agregar el Bundle
        val intent = Intent(context, EliminarDocenteActivity::class.java)
        intent.putExtras(bundle)

        // Iniciar la segunda Activity
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}