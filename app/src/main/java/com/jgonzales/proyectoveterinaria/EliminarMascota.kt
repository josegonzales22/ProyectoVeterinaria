package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EliminarMascota : AppCompatActivity() {

    lateinit var imgRetrocederMasEliminar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_mascota)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederMasEliminar = findViewById(R.id.imgRetrocederMasEliminar)
        imgRetrocederMasEliminar.setOnClickListener{
            finish()
        }
    }
}