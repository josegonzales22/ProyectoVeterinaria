package com.jgonzales.proyectoveterinaria

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.entidades.Medico

class AdaptadorMedico:RecyclerView.Adapter<AdaptadorMedico.MiViewHolder>() {
    private var listaMedico:ArrayList<Medico> = ArrayList()
    private lateinit var context:Context

    fun contexto(context: Context){this.context = context}
    fun agregarItems(items:ArrayList<Medico>){this.listaMedico = items}
    class MiViewHolder(var view:View):RecyclerView.ViewHolder(view) {
        private var dniMedico = view.findViewById<TextView>(R.id.filaMedDni)
        private var nombreMedico = view.findViewById<TextView>(R.id.filaMedNom)
        private var correoMedico = view.findViewById<TextView>(R.id.filaMedCorreo)
        private var celularMedico = view.findViewById<TextView>(R.id.filaMedCel)
        fun bindView ( medico: Medico){
            this.dniMedico.text = medico.dniMedico
            this.nombreMedico.text = medico.nombreMedico + " " + medico.apellidoMedico
            this.correoMedico.text = medico.correoMedico
            this.celularMedico.text = medico.celularMedico
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= AdaptadorMedico.MiViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fila_medicos, parent, false)
    )

    override fun onBindViewHolder(holder: AdaptadorMedico.MiViewHolder, position: Int) {
        val medicoItem = listaMedico[position]
        holder.bindView(medicoItem)
    }

    override fun getItemCount(): Int {
        return listaMedico.size
    }
}