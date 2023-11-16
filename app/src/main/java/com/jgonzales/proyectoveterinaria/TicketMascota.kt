package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

class TicketMascota : AppCompatActivity() {

    lateinit var imgRetrocederMasTicket: ImageView
    lateinit var btnGenerar:Button
    var tipo:String = "Ejemplo de medicina 1"

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

        spinnerMascota.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipo = items[position]
            }
        }

        inicializar()
        eventoOnClick()
    }

    private fun eventoOnClick() {
        imgRetrocederMasTicket.setOnClickListener{
            finish()
        }
        btnGenerar.setOnClickListener {
            Toast.makeText(this, tipo, Toast.LENGTH_SHORT).show()
        }
    }

    fun inicializar(){
        imgRetrocederMasTicket = findViewById(R.id.imgRetrocederMasTicket)
        btnGenerar = findViewById(R.id.btnGenerar)
    }

}