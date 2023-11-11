package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner

class TicketMascota : AppCompatActivity() {

    lateinit var imgRetrocederMasTicket: ImageView

    private val items = arrayOf("Ejemplo de medicina 1", "Ejemplo de medicina  2", "Ejemplo de medicina  3", "Ejemplo de medicina  4", "Ejemplo de medicina  5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_mascota)

        val spinnerMascota: Spinner = findViewById(R.id.spinnerMascota)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMascota.adapter = adapter

        // Establecer la selección por defecto
        spinnerMascota.setSelection(0)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederMasTicket = findViewById(R.id.imgRetrocederMasTicket)
        imgRetrocederMasTicket.setOnClickListener{
            finish()
        }
    }

}