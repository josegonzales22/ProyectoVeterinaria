package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.modelo.MedicoDAO

class ListaMedicoActivity : AppCompatActivity() {
    private lateinit var btnBackMedico : ImageButton
    private lateinit var dao : MedicoDAO
    private lateinit var rvMedicos:RecyclerView

    private var adaptador:AdaptadorMedico?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_medico)
        asignarReferencias()
        mostrarMedicos()
    }
    private fun mostrarMedicos() {
        var listaMedicos = dao.cargarMedicos()
        adaptador?.contexto(this)
        adaptador?.agregarItems(listaMedicos)
    }
    private fun asignarReferencias() {
        btnBackMedico = findViewById(R.id.btnBackMedicos)
        btnBackMedico.setOnClickListener{
            finish()
        }
        dao = MedicoDAO(this)
        rvMedicos = findViewById(R.id.rvListarMedicos)
        rvMedicos.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorMedico()
        rvMedicos.adapter = adaptador
    }
}