package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class activity_nuevo_cliente : AppCompatActivity() {

    lateinit var imgRetrocederCliente:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_cliente)

        inicializar()
    }

    fun inicializar(){
        imgRetrocederCliente = findViewById(R.id.imgRetrocedeCliente)
        imgRetrocederCliente.setOnClickListener{
            finish()
        }
    }

}