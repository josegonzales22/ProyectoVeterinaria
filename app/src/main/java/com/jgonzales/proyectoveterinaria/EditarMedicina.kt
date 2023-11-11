package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EditarMedicina : AppCompatActivity() {

    lateinit var imgRetrocederMediEditar: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_medicina)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederMediEditar = findViewById(R.id.imgRetrocederMediEditar)
        imgRetrocederMediEditar.setOnClickListener{
            finish()
        }
    }
}