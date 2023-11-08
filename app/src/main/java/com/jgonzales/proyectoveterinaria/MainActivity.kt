package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var btnCrearCuenta:TextView
    lateinit var btnIngresar:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializar()
    }
    fun inicializar(){
        btnCrearCuenta = findViewById(R.id.btnRegistrarse)
        btnCrearCuenta.setOnClickListener{
            val intento1 = Intent(this, RegistroUsuario::class.java)
            startActivity(intento1)
        }
        btnIngresar = findViewById(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            val intento2 = Intent(this, ContenedorActivity::class.java)
            startActivity(intento2)
        }
    }
}