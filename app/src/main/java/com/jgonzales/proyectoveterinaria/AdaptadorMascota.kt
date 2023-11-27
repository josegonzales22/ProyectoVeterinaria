package com.jgonzales.proyectoveterinaria

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.entidades.Mascota

class AdaptadorMascota:RecyclerView.Adapter<AdaptadorMascota.MiViewHolder>() {
    private var listaMascotas:ArrayList<Mascota> = ArrayList()
    private lateinit var context:Context
    fun contexto(context: Context){ this.context = context }
    fun agregarItems(items:ArrayList<Mascota>){ this.listaMascotas = items }
    class  MiViewHolder(var view:View):RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.filaMasId)
        private var nombres = view.findViewById<TextView>(R.id.filaMedNom)
        private var especie = view.findViewById<TextView>(R.id.filaMedDni)
        private var raza = view.findViewById<TextView>(R.id.filaMedCorreo)
        private var genero = view.findViewById<TextView>(R.id.filaMedCel)

        fun bindView(mascota: Mascota){
            this.id.text = mascota.idMascota.toString()
            this.nombres.text = mascota.nombreMascota
            this.especie.text = mascota.especieMascota
            this.raza.text = mascota.razaMascota
            this.genero.text = mascota.generoMascota
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ) = AdaptadorMascota.MiViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fila_mascotas, parent, false)
    )

    override fun onBindViewHolder(holder: AdaptadorMascota.MiViewHolder, position: Int) {
        val mascotaItem = listaMascotas[position]
        holder.bindView(mascotaItem)
    }

    override fun getItemCount(): Int {
        return listaMascotas.size
    }
}