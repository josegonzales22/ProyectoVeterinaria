package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class NuevaMascota : AppCompatActivity() {

    lateinit var imgRetrocederMasNueva: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_mascota)
        inicializar()
    }

    fun inicializar(){
        imgRetrocederMasNueva = findViewById(R.id.imgRetrocederMasNueva)
        imgRetrocederMasNueva.setOnClickListener{
            finish()
        }
    }

}