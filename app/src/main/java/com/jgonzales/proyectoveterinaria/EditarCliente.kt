package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EditarCliente : AppCompatActivity() {

    lateinit var imgRetrocederCliEditar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cliente)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederCliEditar = findViewById(R.id.imgRetrocederCliEditar)
        imgRetrocederCliEditar.setOnClickListener{
            finish()
        }
    }

}