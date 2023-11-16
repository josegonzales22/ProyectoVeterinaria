package com.jgonzales.proyectoveterinaria

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.modelo.MascotaDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicinaDAO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NuevaMedicina : AppCompatActivity() {
    lateinit var imgRetrocederMediNuevo: ImageView
    lateinit var txtCodigoMedicina: EditText
    lateinit var txtDescripcionMedicina: EditText
    lateinit var txtVencimientoMedicina: EditText
    lateinit var txtCantidadMedicina: EditText
    lateinit var btnRegistrarMedicina: Button
    lateinit var medicName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_medicina)
        asignarReferencias()
        eventoOnClick()
        val bundle = intent.extras
        medicName = bundle?.getString("medicName").toString()
    }
    fun eventoOnClick(){
        imgRetrocederMediNuevo.setOnClickListener{
            finish()
        }
        btnRegistrarMedicina.setOnClickListener{
            registrarLibro()
            limpiar()
            val intent = Intent(this, ContenedorActivity::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        txtVencimientoMedicina.setOnClickListener{
            interfazFecha()
        }
    }
    fun asignarReferencias(){
        imgRetrocederMediNuevo = findViewById(R.id.imgRetrocederMediNuevo)
        txtCodigoMedicina = findViewById(R.id.txtCodigoMedicina)
        txtDescripcionMedicina = findViewById(R.id.txtDescripcionMedicina)
        txtVencimientoMedicina = findViewById(R.id.txtVencimientoMedicina)
        txtCantidadMedicina = findViewById(R.id.txtCantidadMedicina)
        btnRegistrarMedicina = findViewById(R.id.btnRegistrarMedicina)
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
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            objMedicina.fechaVencimientoMedicina = dateFormat.parse(vencimientoMedicina)
            objMedicina.cantidadMedicina=cantidadMedicina.toInt()
            val MedicinaDAO = MedicinaDAO(this)
            val mensaje = MedicinaDAO.registrarMedicina(objMedicina)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            limpiar()
        }
    }
    private fun limpiar(){
        txtCodigoMedicina.setText("")
        txtDescripcionMedicina.setText("")
        txtVencimientoMedicina.setText("")
        txtCantidadMedicina.setText("")
    }
    fun interfazFecha(){
        val Dialogfecha = DatePickerFragment {year, month, day ->  mostrarResultado(year, month, day)}
        Dialogfecha.show(supportFragmentManager, "DatePicker")
    }
    private fun mostrarResultado(year: Int, month: Int, day: Int) {
        txtVencimientoMedicina.setText("$day-$month-$year")
    }
    class DatePickerFragment(val listener:(year:Int, month:Int, day:Int)->Unit): DialogFragment(), DatePickerDialog.OnDateSetListener{
        override fun onCreateDialog(savedInstanceState: Bundle?):Dialog{
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month, day)
        }
    }
}