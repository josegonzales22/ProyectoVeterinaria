package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgonzales.proyectoveterinaria.modelo.MedicinaDAO

class ListaMedicinaActivity : AppCompatActivity() {
    private lateinit var btnBackMedicinas : ImageButton
    private  lateinit var dao : MedicinaDAO
    private lateinit var rvMedicinas:RecyclerView
    private var adaptador:AdaptadorMedicina?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_medicina)
        asignarReferencias()
        mostrarMedicinas()
    }

    private fun mostrarMedicinas() {
        try{
            var listaMedicinas = dao.cargarMedicinas()
            adaptador?.contexto(this)
            adaptador?.agregarItems(listaMedicinas)
        }catch (ex:Exception){
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }


    }

    private fun asignarReferencias() {
        btnBackMedicinas = findViewById(R.id.btnBackMedicina)
        btnBackMedicinas.setOnClickListener { finish() }
        dao = MedicinaDAO(this)
        rvMedicinas = findViewById(R.id.rvListarMedicinas)
        rvMedicinas.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorMedicina()
        rvMedicinas.adapter = adaptador
    }
}