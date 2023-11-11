package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EliminarMedico : AppCompatActivity() {

    lateinit var imgRetrocederMediEliminar: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_medico)

        inicializar()
    }
    fun inicializar(){
        imgRetrocederMediEliminar = findViewById(R.id.imgRetrocederMediEliminar)
        imgRetrocederMediEliminar.setOnClickListener{
            finish()
        }
    }

}