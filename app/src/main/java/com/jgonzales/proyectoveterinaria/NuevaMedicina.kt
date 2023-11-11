package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class NuevaMedicina : AppCompatActivity() {

    lateinit var imgRetrocederMediNuevo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_medicina)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederMediNuevo = findViewById(R.id.imgRetrocederMediNuevo)
        imgRetrocederMediNuevo.setOnClickListener{
            finish()
        }
    }

}