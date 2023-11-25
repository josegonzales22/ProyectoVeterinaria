package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO

class ListaClientesActivity : AppCompatActivity() {
    private lateinit var btnBackCliente:ImageButton
    private lateinit var rvClientes:RecyclerView
    private  lateinit var dao: ClienteDAO
    private var adaptadorCliente:AdaptadorCliente?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clientes)
        asignarReferencias()
        mostrarClientes()
    }

    private fun mostrarClientes() {
        var listaClientes = dao.cargarClientes()
        adaptadorCliente?.contexto(this)
        adaptadorCliente?.agregarItems(listaClientes)
    }

    private fun asignarReferencias() {
        btnBackCliente = findViewById(R.id.btnBackCliente)
        btnBackCliente.setOnClickListener{
            finish()
        }
        dao = ClienteDAO(this)
        rvClientes = findViewById(R.id.rvListarClientes)
        rvClientes.layoutManager = LinearLayoutManager(this)
        adaptadorCliente = AdaptadorCliente()
        rvClientes.adapter = adaptadorCliente
    }
}