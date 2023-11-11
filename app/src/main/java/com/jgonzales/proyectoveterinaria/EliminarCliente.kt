package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EliminarCliente : AppCompatActivity() {

    lateinit var imgRetrocederCliEliminar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_cliente)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederCliEliminar = findViewById(R.id.imgRetrocederCliEliminar)
        imgRetrocederCliEliminar.setOnClickListener{
            finish()
        }
    }

}