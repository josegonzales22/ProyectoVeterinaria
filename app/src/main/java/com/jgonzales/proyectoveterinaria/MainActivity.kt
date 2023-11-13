package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jgonzales.proyectoveterinaria.modelo.MedicoDAO

class MainActivity : AppCompatActivity() {
    lateinit var btnCrearCuenta:TextView
    lateinit var btnIngresar:Button
    lateinit var txtUsuario:EditText
    lateinit var txtContrasenia:EditText
    lateinit var medicoDAO:MedicoDAO
    lateinit var FInicio:FragmentInicio
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializar()
    }
    fun inicializar(){
        txtUsuario = findViewById(R.id.txtUsuarioU)
        txtContrasenia = findViewById(R.id.txtContraseniaU)
        btnCrearCuenta = findViewById(R.id.btnRegistrarse)
        btnCrearCuenta.setOnClickListener{
            val intento1 = Intent(this, RegistroUsuario::class.java)
            startActivity(intento1)
        }
        btnIngresar = findViewById(R.id.btnNuevoCliente)
        btnIngresar.setOnClickListener {
            Entrar()
        }
        medicoDAO = MedicoDAO(this)
    }
    fun Entrar(){
        var u:String =txtUsuario.text.toString()
        var p:String =txtContrasenia.text.toString()
        if(u.isEmpty()&&p.isEmpty()){
            Toast.makeText(this, "Error: Campos vacíos", Toast.LENGTH_LONG).show()
        }else if(medicoDAO.login(u,p)==1){
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            val intento2 = Intent(this, ContenedorActivity::class.java)
            startActivity(intento2)
        }else{
            Toast.makeText(this, "Usuario y/o contraseña inválidos", Toast.LENGTH_SHORT).show()
        }
    }

}