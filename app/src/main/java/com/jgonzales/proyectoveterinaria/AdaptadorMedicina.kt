package com.jgonzales.proyectoveterinaria

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import java.text.SimpleDateFormat
import java.util.Date

class AdaptadorMedicina:RecyclerView.Adapter<AdaptadorMedicina.MiViewHolder>() {
    private var listaMedicinas : ArrayList<Medicina> = ArrayList()
    private lateinit var context : Context
    fun contexto (context:Context){this.context=context}
    fun agregarItems(items:ArrayList<Medicina>){this.listaMedicinas=items}

    class MiViewHolder(var view : View):RecyclerView.ViewHolder(view) {
        private var idMedicina = view.findViewById<TextView>(R.id.filaMedId)
        private var codigoMedicina = view.findViewById<TextView>(R.id.filaMedDni)
        private var descripcionMedicina = view.findViewById<TextView>(R.id.filaMedNom)
        private var fechaVencimientoMedicina = view.findViewById<TextView>(R.id.filaMedCorreo)
        private var cantidadMedicina = view.findViewById<TextView>(R.id.filaMedCel)
        fun bindView(medicina: Medicina){
            try{
                val fecha: Date =medicina.fechaVencimientoMedicina
                val formato = SimpleDateFormat("dd-MM-yyyy")
                val fechaFormateada = formato.format(fecha)

                this.idMedicina.text = medicina.idMedicina.toString()
                this.codigoMedicina.text = medicina.codigoMedicina
                this.descripcionMedicina.text = medicina.descripcionMedicina
                this.fechaVencimientoMedicina.text = fechaFormateada.toString()
                this.cantidadMedicina.text = medicina.cantidadMedicina.toString()
            }catch (ex:Exception){
                Toast.makeText(null, ex.message, Toast.LENGTH_LONG).show()
            }
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