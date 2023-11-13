package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.entidades.Medico
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicoDAO

class NuevoMedico : AppCompatActivity() {

    lateinit var imgRetrocederMedNuevo: ImageView

    lateinit var txtDNIMedico: EditText
    lateinit var txtNombreMedico: EditText
    lateinit var txtApellidoMedico: EditText
    lateinit var txtCorreoMedico: EditText
    lateinit var txtCelularMedico: EditText
    lateinit var btnRegistrarMedico: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_medico)

        inicializar()

        asignarReferencias()
    }

    fun inicializar(){
        imgRetrocederMedNuevo = findViewById(R.id.imgRetrocederMedNuevo)
        imgRetrocederMedNuevo.setOnClickListener{
            finish()
        }
    }
    fun asignarReferencias(){
        txtDNIMedico = findViewById(R.id.txtDNIMedico)
        txtNombreMedico = findViewById(R.id.txtNombreMedico)
        txtApellidoMedico = findViewById(R.id.txtApellidoMedico)
        txtCorreoMedico = findViewById(R.id.txtCorreoMedico)
        txtCelularMedico = findViewById(R.id.txtCelularMedico)
        btnRegistrarMedico = findViewById(R.id.btnRegistrarMedico)
        btnRegistrarMedico.setOnClickListener{
            registrarLibro()
        }
    }

    private fun registrarLibro(){
        val dniMedico=txtDNIMedico.text.toString()
        val nombreMedico=txtNombreMedico.text.toString()
        val apellidoMedico=txtApellidoMedico.text.toString()
        val correoMedico=txtCorreoMedico.text.toString()
        val celularMedico=txtCelularMedico.text.toString()

        if(dniMedico.isEmpty() || nombreMedico.isEmpty() || apellidoMedico.isEmpty() || correoMedico.isEmpty() || celularMedico.isEmpty()){
            Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            val objMedico = Medico()
            objMedico.dniMedico=dniMedico.toInt()
            objMedico.nombreMedico=nombreMedico
            objMedico.apellidoMedico=apellidoMedico
            objMedico.correoMedico=correoMedico
            objMedico.celularMedico=celularMedico.toInt()
            val medicoDAO = MedicoDAO(this)
            val mensaje = medicoDAO.registrarMedico(objMedico)
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
        txtDNIMedico.setText("")
        txtNombreMedico.setText("")
        txtApellidoMedico.setText("")
        txtCorreoMedico.setText("")
        txtCelularMedico.setText("")
    }


}