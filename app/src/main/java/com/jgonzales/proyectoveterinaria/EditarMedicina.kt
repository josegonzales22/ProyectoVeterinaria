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
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicinaDAO
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditarMedicina : AppCompatActivity() {

    lateinit var imgRetrocederMediEditar: ImageView
    lateinit var etCodigoMedicina: EditText
    lateinit var etDescripcionMedicina: EditText
    lateinit var etFechaVencimientoMedicina: EditText
    lateinit var etCantidadMedicina: EditText
    lateinit var btnEditGuardarMedicina: Button

    //-------RETROCEDER AL DASHBOARD----------
    lateinit var medicName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_medicina)

        inicializar()

        // Obtener datos del Intent
        val idMedicina = intent.getIntExtra("idMedicina", 0)

        val codigoMedicina = intent.getStringExtra("codigoMedicina")
        val descripcionMedicina = intent.getStringExtra("descripcionMedicina")
        val fechaVencimientoMedicina = intent.getStringExtra("fechaVencimientoMedicina")
        val cantidadMedicina = intent.getIntExtra("cantidadMedicina",0)

        etFechaVencimientoMedicina.setOnClickListener {
            interfazFecha()
        }


        // Llenar el formulario con los datos del cliente
        etCodigoMedicina.setText(codigoMedicina)
        etDescripcionMedicina.setText(descripcionMedicina)
        etFechaVencimientoMedicina.setText(fechaVencimientoMedicina)
        etCantidadMedicina.setText(cantidadMedicina.toString())

        //-------RETROCEDER AL DASHBOARD----------
        val bundle = intent.extras
        medicName = bundle?.getString("medicName").toString()

        // Agregar evento de clic al botón guardar
        btnEditGuardarMedicina.setOnClickListener {
            // Obtener los valores actualizados de los campos
            val nuevoCodigo = etCodigoMedicina.text.toString().trim()
            val nuevoDescripcion = etDescripcionMedicina.text.toString().trim()
            val nuevoFechaVencimientoStr = etFechaVencimientoMedicina.text.toString().trim()

            // Convertir la cadena de fecha a un objeto de fecha
            val nuevoFechaVencimiento = convertirStringADate(nuevoFechaVencimientoStr)

            val nuevoCantidad = etCantidadMedicina.text.toString().trim()

            // Verificar que no haya campos vacíos
            if (nuevoCodigo.isNotEmpty() && nuevoDescripcion.isNotEmpty() && nuevoFechaVencimientoStr.isNotEmpty() && nuevoCantidad.isNotEmpty()) {

                val nuevoFechaVencimiento = convertirStringADate(nuevoFechaVencimientoStr)

                // Actualizar la información en la base de datos
                val medicinaDAO = MedicinaDAO(this@EditarMedicina)
                val medicinaActualizado = Medicina().apply {
                    this.idMedicina = intent.getIntExtra("idMedicina", 0)
                    this.codigoMedicina = nuevoCodigo
                    this.descripcionMedicina = nuevoDescripcion
                    this.fechaVencimientoMedicina = nuevoFechaVencimiento
                    this.cantidadMedicina = nuevoCantidad.toInt()
                }

                val respuesta = medicinaDAO.actualizarMedicina(medicinaActualizado)


                // Mostrar el resultado de la actualización
                Toast.makeText(this@EditarMedicina, respuesta, Toast.LENGTH_SHORT).show()

                //-------RETROCEDER AL DASHBOARD----------
                val intent = Intent(this, ContenedorActivity::class.java)
                var bundle:Bundle = Bundle()
                bundle.putString("medicName", medicName)
                intent.putExtras(bundle)
                startActivity(intent)

            } else {
                // Mostrar mensaje si hay campos vacíos
                Toast.makeText(this@EditarMedicina, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@EditarMedicina, "La fecha de vencimiento no puede estar vacía", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun convertirStringADate(fechaStr: String): Date {
        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            return sdf.parse(fechaStr) ?: Date()
        } catch (e: ParseException) {
            // Manejar la excepción, por ejemplo, devolver una fecha predeterminada
            return Date()
        }
    }

    fun inicializar(){
        btnEditGuardarMedicina = findViewById(R.id.btnEditGuardarMedicina)


        imgRetrocederMediEditar = findViewById(R.id.imgRetrocederMediEditar)
        imgRetrocederMediEditar.setOnClickListener{
            finish()
        }

        etCodigoMedicina = findViewById(R.id.editCodigoMedicina)
        etDescripcionMedicina = findViewById(R.id.editDescripcionMedicina)
        etFechaVencimientoMedicina = findViewById(R.id.editVencimientoMedicina)
        etCantidadMedicina= findViewById(R.id.editCantidadMedicina)
    }

    fun interfazFecha(){
        val Dialogfecha = NuevaMedicina.DatePickerFragment { year, month, day ->
            mostrarResultado(
                year,
                month,
                day
            )
        }
        Dialogfecha.show(supportFragmentManager, "DatePicker")
    }

    private fun mostrarResultado(year: Int, month: Int, day: Int) {
        etFechaVencimientoMedicina.setText("$day-$month-$year")
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