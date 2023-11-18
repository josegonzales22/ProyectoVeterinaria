package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
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

    lateinit var medicName:String
    var tipo:String = "Macho"
    var generoMascota:String = "Macho"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_mascota)
        inicializar()
        asignarReferencias()
        val bundle = intent.extras
        medicName = bundle?.getString("medicName").toString()

        val spinnerMascota: Spinner = findViewById(R.id.txtGeneroMascota)
        val opciones = resources.getStringArray(R.array.opciones)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMascota.adapter = adapter
        spinnerMascota.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipo = opciones[position]
            }
        }
        spinnerMascota.setSelection(0)
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
        btnRegistrarMascota = findViewById(R.id.btnRegistrarMascota)
        btnRegistrarMascota.setOnClickListener{
            registrarLibro()
            val intent = Intent(this, ContenedorActivity::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
    private fun registrarLibro(){
        val nombreMascota=txtNombreMascota.text.toString()
        val especieMascota=txtEspecieMascota.text.toString()
        val razaMascota=txtRazaMascota.text.toString()

        if(nombreMascota.isEmpty() || especieMascota.isEmpty() || razaMascota.isEmpty()){
            Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            val objMascota = Mascota()
            objMascota.nombreMascota=nombreMascota
            objMascota.especieMascota=especieMascota
            objMascota.razaMascota=razaMascota
            objMascota.generoMascota=tipo
            val MascotaDAO = MascotaDAO(this)
            val mensaje = MascotaDAO.registrarMascota(objMascota)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            limpiar()
        }
    }
    private fun limpiar(){
        txtNombreMascota.setText("")
        txtEspecieMascota.setText("")
        txtRazaMascota.setText("")
        txtGeneroMascota.setText("")
    }
}