package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EditarMascota : AppCompatActivity() {

    lateinit var imgRetrocederMasEditar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederMasEditar = findViewById(R.id.imgRetrocederMasEditar)
        imgRetrocederMasEditar.setOnClickListener{
            finish()
        }
    }

}