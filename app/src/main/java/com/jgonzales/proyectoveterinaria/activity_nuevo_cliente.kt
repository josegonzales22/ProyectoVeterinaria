package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO

class activity_nuevo_cliente : AppCompatActivity() {

    lateinit var imgRetrocederCliente:ImageView

    lateinit var txtDNICliente: EditText
    lateinit var txtNombreCliente: EditText
    lateinit var txtApellidoCliente: EditText
    lateinit var txtCorreoCliente: EditText
    lateinit var txtCelularCliente:EditText
    lateinit var btnRegistrarCliente:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_cliente)

        inicializar()

        asignarReferencias()
    }
    fun inicializar(){
        imgRetrocederCliente = findViewById(R.id.imgRetrocedeCliente)
        imgRetrocederCliente.setOnClickListener{
            finish()
        }
    }

    fun asignarReferencias(){
        txtDNICliente = findViewById(R.id.txtDNICliente)
        txtNombreCliente = findViewById(R.id.txtNombreCliente)
        txtApellidoCliente = findViewById(R.id.txtApellidoCliente)
        txtCorreoCliente = findViewById(R.id.txtCorreoCliente)
        txtCelularCliente = findViewById(R.id.txtCelularCliente)
        btnRegistrarCliente = findViewById(R.id.btnRegistrarCliente)
        btnRegistrarCliente.setOnClickListener{
            registrarLibro()
        }
    }

    private fun registrarLibro(){
        val dniCliente=txtDNICliente.text.toString()
        val nombreCliente=txtNombreCliente.text.toString()
        val apellidoCliente=txtApellidoCliente.text.toString()
        val correoCliente=txtCorreoCliente.text.toString()
        val celularCliente=txtCelularCliente.text.toString()

        if(dniCliente.isEmpty() || nombreCliente.isEmpty() || apellidoCliente.isEmpty() || correoCliente.isEmpty() || celularCliente.isEmpty()){
            Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            val objCliente = Cliente()
            objCliente.dniCliente=dniCliente.toInt()
            objCliente.nombreCliente=nombreCliente
            objCliente.apellidoCliente=apellidoCliente
            objCliente.correoCliente=correoCliente
            objCliente.celularCliente=celularCliente.toInt()
            val clienteDAO = ClienteDAO(this)
            val mensaje = clienteDAO.registrarCliente(objCliente)
            mostrarMensaje(mensaje)
            limpiar()
        }
    }

    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("NOTIFICACIÓN")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", null)
        ventana.create()
        ventana.show()
    }
    private fun limpiar(){
        txtDNICliente.setText("")
        txtNombreCliente.setText("")
        txtApellidoCliente.setText("")
        txtCorreoCliente.setText("")
        txtCelularCliente.setText("")
    }

}