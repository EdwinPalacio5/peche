package com.ues.gpo7fb16014.crud.materia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ues.gpo7fb16014.crud.alumno.AlumnoListAdapter
import com.ues.gpo7fb16014.crud.docente.EditarDocenteActivity
import com.ues.gpo7fb16014.crud.docente.EliminarDocenteActivity
import com.ues.gpo7fb16014.databinding.AlumnoItemBinding
import com.ues.gpo7fb16014.databinding.MateriaItemBinding
import com.ues.gpo7fb16014.models.Docente
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


            btnEditar.setOnClickListener {
                startActivityEditar(item)
            }

            btnEliminar.setOnClickListener {
                startActivityEliminar(item)
            }
        }
    }

    private fun startActivityEditar(item: Materia) {
        // Crear el Bundle
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("cod_materia", item.cod_materia)
        bundle.putString("nombre", item.nombre)
        bundle.putString("area", item.area)

        // Crear el Intent y agregar el Bundle
        val intent = Intent(context, EditarMateriaActivity::class.java)
        intent.putExtras(bundle)

        // Iniciar la segunda Activity
        context.startActivity(intent)
    }

    private fun startActivityEliminar(item: Materia) {
        // Crear el Bundle
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("cod_materia", item.cod_materia)
        bundle.putString("nombre", item.nombre)
        bundle.putString("area", item.area)

        // Crear el Intent y agregar el Bundle
        val intent = Intent(context, EliminarMateriaActivity::class.java)
        intent.putExtras(bundle)

        // Iniciar la segunda Activity
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}