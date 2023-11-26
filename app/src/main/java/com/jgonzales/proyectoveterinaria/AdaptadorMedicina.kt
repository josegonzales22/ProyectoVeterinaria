package com.jgonzales.proyectoveterinaria

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import java.util.Date

class AdaptadorMedicina:RecyclerView.Adapter<AdaptadorMedicina.MiViewHolder>() {
    private var listaMedicinas : ArrayList<Medicina> = ArrayList()
    private lateinit var context : Context
    fun contexto (context:Context){this.context=context}
    fun agregarItems(items:ArrayList<Medicina>){this.listaMedicinas=items}

    class MiViewHolder(var view : View):RecyclerView.ViewHolder(view) {
        private var idMedicina = view.findViewById<TextView>(R.id.filaMedId)
        private var codigoMedicina = view.findViewById<TextView>(R.id.filaMedCod)
        private var descripcionMedicina = view.findViewById<TextView>(R.id.filaMedDes)
        private var fechaVencimientoMedicina = view.findViewById<TextView>(R.id.filaMedVenc)
        private var cantidadMedicina = view.findViewById<TextView>(R.id.filaMedCant)
        fun bindView(medicina: Medicina){
            this.idMedicina.text = medicina.idMedicina.toString()
            this.codigoMedicina.text = medicina.codigoMedicina
            this.descripcionMedicina.text = medicina.descripcionMedicina
            this.fechaVencimientoMedicina.text = medicina.fechaVencimientoMedicina.toString()
            this.cantidadMedicina.text = medicina.cantidadMedicina.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int )= AdaptadorMedicina.MiViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fila_medicina, parent, false)
    )

    override fun onBindViewHolder(holder: AdaptadorMedicina.MiViewHolder, position: Int) {
        val medicinaItem = listaMedicinas[position]
        holder.bindView(medicinaItem)
    }

    override fun getItemCount(): Int {
        return listaMedicinas.size
    }
}