package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.modelo.MascotaDAO

class ListaMascotaActivity : AppCompatActivity() {
    private lateinit var btnBackMascota:ImageButton
    private lateinit var dao : MascotaDAO
    private lateinit var rvMascotas:RecyclerView
    private var adaptador:AdaptadorMascota?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_mascota)
        asignarReferencias()
        mostrarMascotas()
    }

    private fun mostrarMascotas() {
        var listaMascotas = dao.cargarMascotas()
        adaptador?.contexto(this)
        adaptador?.agregarItems(listaMascotas)
    }

    private fun asignarReferencias() {
        btnBackMascota = findViewById(R.id.btnBackMascota)
        btnBackMascota.setOnClickListener { finish() }
        dao = MascotaDAO(this)
        rvMascotas = findViewById(R.id.rvListarMascotas)
        rvMascotas.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorMascota()
        rvMascotas.adapter = adaptador
    }
}