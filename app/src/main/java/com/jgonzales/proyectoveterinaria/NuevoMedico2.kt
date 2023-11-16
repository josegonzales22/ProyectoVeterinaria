package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jgonzales.proyectoveterinaria.entidades.Medico
import com.jgonzales.proyectoveterinaria.modelo.MedicoDAO

class NuevoMedico2 : AppCompatActivity() {
    lateinit var dni:String
    lateinit var contrasenia:String

    lateinit var txtNombreMedico: EditText
    lateinit var txtApellidoMedico: EditText
    lateinit var txtCorreoMedico: EditText
    lateinit var txtCelularMedico: EditText
    lateinit var btnRegistrarMedico: Button

    lateinit var imgRetrocederMedNuevo: ImageView

    lateinit var medicName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_medico2)
        val bundle = intent.extras
        val dni2 = bundle?.getString("medicDni")
        val contrasenia2 = bundle?.getString("medicContrasenia")
        medicName = bundle?.getString("medicName").toString()
        dni=dni2.toString()
        contrasenia=contrasenia2.toString()
        asignarReferencias()
        eventoOnClick()
    }
    fun asignarReferencias(){
        txtNombreMedico = findViewById(R.id.txtNombreMedico)
        txtApellidoMedico = findViewById(R.id.txtApellidoMedico)
        txtCorreoMedico = findViewById(R.id.txtCorreoMedico)
        txtCelularMedico = findViewById(R.id.txtCelularMedico)
        btnRegistrarMedico = findViewById(R.id.btnRegistrarMedico)
    }
    fun eventoOnClick(){
        btnRegistrarMedico.setOnClickListener{
            registrarLibro()
            val intent = Intent(this, ContenedorActivity::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        imgRetrocederMedNuevo = findViewById(R.id.imgRetrocederMedNuevo)
        imgRetrocederMedNuevo.setOnClickListener{
            val intento2 = Intent(this, NuevoMedico::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intento2.putExtras(bundle)
            startActivity(intento2)
        }
    }
    private fun registrarLibro(){
        val dniMedico=dni
        val contraMedico=contrasenia
        val nombreMedico=txtNombreMedico.text.toString()
        val apellidoMedico=txtApellidoMedico.text.toString()
        val correoMedico=txtCorreoMedico.text.toString()
        val celularMedico=txtCelularMedico.text.toString()

        if(dniMedico.isEmpty() || nombreMedico.isEmpty() || apellidoMedico.isEmpty() || correoMedico.isEmpty() || celularMedico.isEmpty() || contraMedico.isEmpty()){
            Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            val objMedico = Medico()
            objMedico.dniMedico=dniMedico
            objMedico.contrasenia=contraMedico
            objMedico.nombreMedico=nombreMedico
            objMedico.apellidoMedico=apellidoMedico
            objMedico.correoMedico=correoMedico
            objMedico.celularMedico=celularMedico
            val medicoDAO = MedicoDAO(this)
            val mensaje = medicoDAO.registrarMedico(objMedico)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            limpiar()
        }
    }
    private fun limpiar(){
        txtNombreMedico.setText("")
        txtApellidoMedico.setText("")
        txtCorreoMedico.setText("")
        txtCelularMedico.setText("")
    }
}