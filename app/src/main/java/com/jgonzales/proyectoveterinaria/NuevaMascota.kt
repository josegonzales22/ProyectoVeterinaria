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
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO
import com.jgonzales.proyectoveterinaria.modelo.MascotaDAO

class NuevaMascota : AppCompatActivity() {

    lateinit var imgRetrocederMasNueva: ImageView

    lateinit var txtNombreMascota: EditText
    lateinit var txtEspecieMascota: EditText
    lateinit var txtRazaMascota: EditText
    lateinit var txtGeneroMascota: EditText
    lateinit var btnRegistrarMascota: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_mascota)

        inicializar()

        asignarReferencias()
    }

    fun inicializar(){
        imgRetrocederMasNueva = findViewById(R.id.imgRetrocederMasNueva)
        imgRetrocederMasNueva.setOnClickListener{
            finish()
        }
    }
    fun asignarReferencias(){
        txtNombreMascota = findViewById(R.id.txtNombreMascota)
        txtEspecieMascota = findViewById(R.id.txtEspecieMascota)
        txtRazaMascota = findViewById(R.id.txtRazaMascota)
        txtGeneroMascota = findViewById(R.id.txtGeneroMascota)
        btnRegistrarMascota = findViewById(R.id.btnRegistrarMascota)
        btnRegistrarMascota.setOnClickListener{
            registrarLibro()
        }
    }
    private fun registrarLibro(){
        val nombreMascota=txtNombreMascota.text.toString()
        val especieMascota=txtEspecieMascota.text.toString()
        val razaMascota=txtRazaMascota.text.toString()
        val generoMascota=txtGeneroMascota.text.toString()

        if(nombreMascota.isEmpty() || especieMascota.isEmpty() || razaMascota.isEmpty() || generoMascota.isEmpty()){
            Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            val objMascota = Mascota()
            objMascota.nombreMascota=nombreMascota
            objMascota.especieMascota=especieMascota
            objMascota.razaMascota=razaMascota
            objMascota.generoMascota=generoMascota
            val MascotaDAO = MascotaDAO(this)
            val mensaje = MascotaDAO.registrarMascota(objMascota)
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
        txtNombreMascota.setText("")
        txtEspecieMascota.setText("")
        txtRazaMascota.setText("")
        txtGeneroMascota.setText("")
    }

}