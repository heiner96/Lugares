package com.lugares_u.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lugares_u.databinding.FragmentAddLugarBinding
import com.lugares_u.databinding.LugarFilaBinding
import com.lugares_u.model.Lugar

class LugarAdatper : RecyclerView.Adapter<LugarAdatper.LugarViewHolder>() {

    //la lista para presentar la informacion de los lugares
    private var listaLugares = emptyList<Lugar>()

    inner class LugarViewHolder(private val itemBinding: LugarFilaBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
            fun bind(lugar : Lugar){
                itemBinding.tvNombre.text = lugar.nombre
                itemBinding.tvCorreoLugarFila.text = lugar.correo
                itemBinding.tvTelefonoLugar.text = lugar.telefono
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        //creo un elemeto en memoria de una "cajita" vista_fila
        val itemBinding = LugarFilaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //retorno la cajita en memoria
        return LugarViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {
        //Obtengo el objeto que debo "dibujar" en la fila del recyclerView que "voy"
        val lugarActual = listaLugares[position]

        holder.bind(lugarActual) //LLamo a la función que efectivamente "pinta" la inflate

    }

    override fun getItemCount(): Int {
        return listaLugares.size
    }

    fun setData(lugares : List<Lugar>){
        this.listaLugares = lugares
        notifyDataSetChanged()
    }
}