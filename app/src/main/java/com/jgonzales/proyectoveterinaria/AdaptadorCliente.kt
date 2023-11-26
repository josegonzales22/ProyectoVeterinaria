package com.jgonzales.proyectoveterinaria

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.entidades.Cliente

class AdaptadorCliente : RecyclerView.Adapter<AdaptadorCliente.MiViewHolder>(){
    private var listaClientes:ArrayList<Cliente> = ArrayList()
    private lateinit var context: Context

    fun contexto(context: Context){ this.context = context }
    fun agregarItems(items:ArrayList<Cliente>){ this.listaClientes = items }
    class MiViewHolder(var view: View):RecyclerView.ViewHolder(view){
        private var nombres = view.findViewById<TextView>(R.id.filaMedDes)
        private var dni = view.findViewById<TextView>(R.id.filaMedCod)
        private var correo = view.findViewById<TextView>(R.id.filaMedVenc)
        private var celular = view.findViewById<TextView>(R.id.filaMedCant)

        fun bindView (cliente: Cliente){
            this.nombres.text = cliente.nombreCliente + " " + cliente.apellidoCliente
            this.dni.text = cliente.dniCliente.toString()
            this.correo.text = cliente.correoCliente
            this.celular.text = cliente.celularCliente.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ) = AdaptadorCliente.MiViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fila_clientes, parent, false)
    )
    override fun onBindViewHolder(holder: AdaptadorCliente.MiViewHolder, position: Int) {
        val clienteItem = listaClientes[position]
        holder.bindView(clienteItem)
    }

    override fun getItemCount(): Int {
        return listaClientes.size
    }
}