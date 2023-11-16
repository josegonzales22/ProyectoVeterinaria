package com.jgonzales.proyectoveterinaria

import android.content.Intent
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

    lateinit var txtContraseniaMedico:EditText
    lateinit var btnRegistrarMedico: Button
    lateinit var medicName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_medico)
        inicializar()
        asignarReferencias()
        val bundle = intent.extras
        medicName = bundle?.getString("medicName").toString()
    }

    fun inicializar(){
        imgRetrocederMedNuevo = findViewById(R.id.imgRetrocederMedNuevo)
        imgRetrocederMedNuevo.setOnClickListener{
            //finish()
            val intento3 = Intent(this, ContenedorActivity::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intento3.putExtras(bundle)
            startActivity(intento3)
        }
    }
    fun asignarReferencias(){
        txtDNIMedico = findViewById(R.id.txtDNIMedico)
        txtContraseniaMedico = findViewById(R.id.txtContrasenia)
        btnRegistrarMedico = findViewById(R.id.btnCambiarMedico)
        btnRegistrarMedico.setOnClickListener{
            mandarDatos()
            //registrarLibro()
        }
    }
    private fun mandarDatos(){
        var dni1:String=txtDNIMedico.text.toString()
        var contrasenia2:String=txtContraseniaMedico.text.toString()
        val intento2 = Intent(this, NuevoMedico2::class.java)
        var bundle:Bundle = Bundle()
        var dni:String? = dni1
        var contrasenia:String? = contrasenia2
        bundle.putString("medicDni", dni1)
        bundle.putString("medicContrasenia", contrasenia2)
        bundle.putString("medicName", medicName)
        intento2.putExtras(bundle)
        startActivity(intento2)
    }



}