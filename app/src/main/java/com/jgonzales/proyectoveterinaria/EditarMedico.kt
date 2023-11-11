package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EditarMedico : AppCompatActivity() {

    lateinit var imgRetrocederMediEditar: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_medico)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederMediEditar = findViewById(R.id.imgRetrocederMediEditar)
        imgRetrocederMediEditar.setOnClickListener{
            finish()
        }
    }

}