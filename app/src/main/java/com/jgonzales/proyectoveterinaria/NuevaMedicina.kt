package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.modelo.MascotaDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicinaDAO
import java.text.SimpleDateFormat
import java.util.Locale

class NuevaMedicina : AppCompatActivity() {

    lateinit var imgRetrocederMediNuevo: ImageView

    lateinit var txtCodigoMedicina: EditText
    lateinit var txtDescripcionMedicina: EditText
    lateinit var txtVencimientoMedicina: EditText
    lateinit var txtCantidadMedicina: EditText
    lateinit var btnRegistrarMedicina: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_medicina)

        inicializar()

        asignarReferencias()
    }

    fun inicializar(){
        imgRetrocederMediNuevo = findViewById(R.id.imgRetrocederMediNuevo)
        imgRetrocederMediNuevo.setOnClickListener{
            finish()
        }
    }
    fun asignarReferencias(){
        txtCodigoMedicina = findViewById(R.id.txtCodigoMedicina)
        txtDescripcionMedicina = findViewById(R.id.txtDescripcionMedicina)
        txtVencimientoMedicina = findViewById(R.id.txtVencimientoMedicina)
        txtCantidadMedicina = findViewById(R.id.txtCantidadMedicina)
        btnRegistrarMedicina = findViewById(R.id.btnRegistrarMedicina)
        btnRegistrarMedicina.setOnClickListener{
            registrarLibro()
        }
    }
    private fun registrarLibro(){
        val codigoMedicina=txtCodigoMedicina.text.toString()
        val descripcionMedicina=txtDescripcionMedicina.text.toString()
        val vencimientoMedicina=txtVencimientoMedicina.text.toString()
        val cantidadMedicina=txtCantidadMedicina.text.toString()

        val vencimientoMedicinaString = txtVencimientoMedicina.text.toString()


        if(codigoMedicina.isEmpty() || descripcionMedicina.isEmpty() || vencimientoMedicina.isEmpty() || cantidadMedicina.isEmpty()){
            Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            val objMedicina = Medicina()
            objMedicina.codigoMedicina=codigoMedicina
            objMedicina.descripcionMedicina=descripcionMedicina

            // Parsear la cadena de fecha a un objeto Date
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            objMedicina.fechaVencimientoMedicina = dateFormat.parse(vencimientoMedicina)


            objMedicina.cantidadMedicina=cantidadMedicina.toInt()
            val MedicinaDAO = MedicinaDAO(this)
            val mensaje = MedicinaDAO.registrarMedicina(objMedicina)
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
        txtCodigoMedicina.setText("")
        txtDescripcionMedicina.setText("")
        txtVencimientoMedicina.setText("")
        txtCantidadMedicina.setText("")
    }

}