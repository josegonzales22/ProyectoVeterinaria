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

    lateinit var medicName:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_cliente)
        inicializar()
        asignarReferencias()
        val bundle = intent.extras
        medicName = bundle?.getString("medicName").toString()
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
            val intent = Intent(this, ContenedorActivity::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intent.putExtras(bundle)
            startActivity(intent)
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
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            limpiar()
        }
    }
    private fun limpiar(){
        txtDNICliente.setText("")
        txtNombreCliente.setText("")
        txtApellidoCliente.setText("")
        txtCorreoCliente.setText("")
        txtCelularCliente.setText("")
    }

}