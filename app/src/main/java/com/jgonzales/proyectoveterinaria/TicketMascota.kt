package com.jgonzales.proyectoveterinaria

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.entidades.Medico
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO
import com.jgonzales.proyectoveterinaria.modelo.MascotaDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicinaDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicoDAO
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TicketMascota : AppCompatActivity() {
    /*
    * FUNCIONAMIENTO
    * ==============
    * Recolectar todos los datos de los editText
    *
    * Validar:
    *   Si EXISTE:
    *       medicina
    *       mascota
    *       cliente
    *       Medico
    * Crear una función que valide cada tabla y retornar la información dentro de su objeto
    * correspondiente
    *
    * Validar:
    *   SI EXISTE:
    *       disponibilidad de la cantidad de medicina solicitada
    * */
    lateinit var imgRetrocederMasTicket: ImageView
    lateinit var btnExportar:Button

    var tituloText:String = "Ticket de atención"

    lateinit var medicName:String
    lateinit var medicDni:String
    lateinit var medicDate:String

    lateinit var customName:String
    lateinit var customDni:String

    lateinit var petName:String
    lateinit var petId:String

    lateinit var medicineDesc:String
    lateinit var medicineCant:String

    lateinit var txtIdMedicina : EditText
    lateinit var txtCantMedicina : EditText
    lateinit var txtIdMascota : EditText
    lateinit var txtDniMedico : EditText
    lateinit var txtDniCliente : EditText

    val medicinaDAO :MedicinaDAO = MedicinaDAO(this@TicketMascota)
    lateinit var medicinaExistente:Medicina
    val mascotaDAO : MascotaDAO = MascotaDAO(this@TicketMascota)
    lateinit var mascotaExistente : Mascota
    val medicoDAO : MedicoDAO = MedicoDAO(this@TicketMascota)
    lateinit var medicoExistente : Medico
    val clienteDAO : ClienteDAO = ClienteDAO(this@TicketMascota)
    lateinit var clienteExistente : Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_mascota)
        inicializar()
        eventoOnClick()
        if(checkPermission()){
            Toast.makeText(this, "Permiso aceptado", Toast.LENGTH_SHORT).show()
        }else{
            requestPermissions()
        }
    }

    private fun eventoOnClick() {
        imgRetrocederMasTicket.setOnClickListener{
            finish()
        }
        btnExportar.setOnClickListener {
            if(obtenerInformacion()){
                try{
                    exportarPDF()
                    medicinaExistente.cantidadMedicina=medicinaExistente.cantidadMedicina-txtCantMedicina.text.toString().toInt()
                    var resp : String = medicinaDAO.actualizarMedicina(medicinaExistente)
                    Toast.makeText(this@TicketMascota, "Se creó el ticket correctamente", Toast.LENGTH_SHORT).show()
                }catch (ex:Exception){
                    Toast.makeText(this@TicketMascota, "Ocurrio un error", Toast.LENGTH_SHORT).show()
                }finally {
                    val intent = Intent(this, ContenedorActivity::class.java)
                    var bundle:Bundle = Bundle()
                    bundle.putString("medicName", medicName)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this@TicketMascota, "Verifique la información ingresada", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun obtenerInformacion() : Boolean{
        var medicinaValidada : Boolean = false
        var mascotaValidada : Boolean = false
        var medicoValidado : Boolean = false
        var clienteValidado : Boolean = false
        var cantMedValidado : Boolean = false
        if(!txtIdMedicina.text.isEmpty() ||
            !txtCantMedicina.text.isEmpty() ||
            !txtIdMascota.text.isEmpty() ||
            !txtDniMedico.text.isEmpty() ||
            !txtDniCliente.text.isEmpty()){
            try{
                medicinaExistente = medicinaDAO.obtenerClientePorId(txtIdMedicina.text.toString().toInt())
                if(medicinaExistente!=null){
                    medicinaValidada = true
                    if(medicinaExistente.cantidadMedicina>=txtCantMedicina.text.toString().toInt()){cantMedValidado=true}
                    else{Toast.makeText(this@TicketMascota, "Sin stock suficiente", Toast.LENGTH_SHORT).show()}
                }
            }catch (ex:Exception){
                Toast.makeText(this@TicketMascota, "Ocurrio un error o la medicina no existe", Toast.LENGTH_SHORT).show()
            }
            try {
                mascotaExistente = mascotaDAO.obtenerMascotaPorId(txtIdMascota.text.toString().toInt())
                if(mascotaExistente!=null){mascotaValidada = true }
            }catch (ex:Exception){
                Toast.makeText(this@TicketMascota, "Ocurrio un error o la mascota no existe", Toast.LENGTH_SHORT).show()
            }
            try{
                medicoExistente = medicoDAO.obtenerMedicoPorDni(txtDniMedico.text.toString().toInt())
                if(medicoExistente!=null){medicoValidado = true }
            }catch (ex: Exception){
                Toast.makeText(this@TicketMascota, "Ocurrio un error o el médico no existe", Toast.LENGTH_SHORT).show()
            }
            try{
                clienteExistente = clienteDAO.obtenerClientePorDni(txtDniCliente.text.toString().toInt())
                if(clienteExistente!=null){clienteValidado = true }
            }catch (ex: Exception){
                Toast.makeText(this@TicketMascota, "Ocurrio un error o el cliente no existe", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this@TicketMascota, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
        return medicinaValidada && mascotaValidada && medicoValidado && clienteValidado && cantMedValidado
    }
    fun inicializar(){
        imgRetrocederMasTicket = findViewById(R.id.imgRetrocederMasTicket)
        btnExportar = findViewById(R.id.btnGenerar)
        txtIdMedicina = findViewById(R.id.txtIdMedicina)
        txtCantMedicina = findViewById(R.id.txtCantMedicina)
        txtIdMascota = findViewById(R.id.txtIdMascota)
        txtDniMedico = findViewById(R.id.txtDniMedico)
        txtDniCliente = findViewById(R.id.txtDniCliente)
    }
    private fun requestPermissions(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            200
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 200){
            if(grantResults.size>0){
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if(writeStorage && readStorage) {
                    Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, "Permisos rechazados", Toast.LENGTH_LONG)
                    finish()
                }
            }
        }
    }
    private fun checkPermission():Boolean{
        val permission1 = ContextCompat.checkSelfPermission(applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permission2 = ContextCompat.checkSelfPermission(applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }
    private fun exportarPDF() {
        /*-------------------------------------*/
        var lineaMedico:String
        lineaMedico  = "┌                           ───────────────────────────"
        var carA:String = "│"
        var carB:String = "┘"
        var carC:String = "┐"
        var linea2:String
        linea2       = "└────────────────────────────────────"
        /*-------------------------------------*/
        val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a"))
        medicName = medicoExistente.nombreMedico + " " + medicoExistente.apellidoMedico
        medicDni = medicoExistente.dniMedico
        medicDate = dateTime

        customName = clienteExistente.nombreCliente + " " + clienteExistente.apellidoCliente
        customDni = clienteExistente.dniCliente.toString()

        petName = mascotaExistente.nombreMascota
        petId = mascotaExistente.idMascota.toString()

        medicineDesc = medicinaExistente.descripcionMedicina
        medicineCant = "x" + medicinaExistente.cantidadMedicina
        /*-------------------------------------*/
        var pdfDocument = PdfDocument()
        var paint = Paint()
        var titulo = TextPaint()
        var lineaDoc = TextPaint()
        var car1a = TextPaint()
        var lineaDoc2 = TextPaint()


        var fechaPDF= TextPaint()
        var medic = TextPaint()
        var custom = TextPaint()
        var pet = TextPaint()
        var medicine = TextPaint()

        /*-------------------------------------*/
        var paginaInfo = PdfDocument.PageInfo.Builder(1440, 2200, 1).create()
        var pagina1 = pdfDocument.startPage(paginaInfo)

        var canvas = pagina1.canvas

        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.iconopdf)
        var bitmapEscala = Bitmap.createScaledBitmap(bitmap, 783,249, false)
        canvas.drawBitmap(bitmapEscala, 328f, 75f, paint)

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        titulo.textSize = 96f
        canvas.drawText(tituloText, 319f, 390f, titulo)

        var vLMedico:Float =469f

        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        lineaDoc.textSize=50f


        car1a.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        car1a.textSize=50f

        lineaDoc2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        lineaDoc2.textSize=50f


        /*-------------------------------------*/
        /*              MÉDICO                  */
        /*-------------------------------------*/
        medic.textSize=45f
        medic.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        canvas.drawText("Nombre completo", 85f, vLMedico+70f, medic)
        medic.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(medicName, 85f, vLMedico+130f, medic)

        medic.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        canvas.drawText("Documento Nacional de Identidad", 85f, vLMedico+200f, medic)
        medic.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(medicDni, 85f, vLMedico+260f, medic)

        medic.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        canvas.drawText("Fecha de atención", 85f, vLMedico+340f, medic)
        medic.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(dateTime, 85f, vLMedico+400f, medic)

        /*-----------CUADRO-----------*/
        canvas.drawText(lineaMedico, 40f, vLMedico,lineaDoc )
        canvas.drawText(carC, 1337f, vLMedico,lineaDoc )
        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC))
        lineaDoc.textSize = 40f
        canvas.drawText("Médico", 150f, vLMedico,lineaDoc )
        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        lineaDoc.textSize = 50f
        canvas.drawText(carA, 40f, vLMedico+50f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+50f,car1a )
        canvas.drawText(carA, 40f, vLMedico+100f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+100f,car1a )
        canvas.drawText(carA, 40f, vLMedico+150f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+150f,car1a )
        canvas.drawText(carA, 40f, vLMedico+200f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+200f,car1a )
        canvas.drawText(carA, 40f, vLMedico+250f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+250f,car1a )
        canvas.drawText(carA, 40f, vLMedico+300f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+300f,car1a )
        canvas.drawText(carA, 40f, vLMedico+350f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+350f,car1a )
        canvas.drawText(carA, 40f, vLMedico+400f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+400f,car1a )
        canvas.drawText(linea2, 40f, vLMedico+450f,lineaDoc2 )
        canvas.drawText(carB, 1337f, vLMedico+450f,car1a )
        //canvas.drawText(carC, 1337f, vLMedico+50f,car1a )

        /*-------------------------------------*/
        /*              CLIENTE                  */
        /*-------------------------------------*/
        custom.textSize=45f

        custom.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        canvas.drawText("Nombre completo", 85f, vLMedico+620f, custom)
        custom.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(customName, 85f, vLMedico+680f, custom)

        custom.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        canvas.drawText("Documento Nacional de Identidad", 85f, vLMedico+750f, custom)
        custom.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(customDni, 85f, vLMedico+810f, custom)


        /*-----------CUADRO-----------*/
        canvas.drawText(lineaMedico, 40f, vLMedico+550f,lineaDoc )
        canvas.drawText(carC, 1337f, vLMedico+550f,lineaDoc )
        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC))
        lineaDoc.textSize = 40f
        canvas.drawText("Cliente", 150f, vLMedico+550f,lineaDoc )
        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        lineaDoc.textSize = 50f
        canvas.drawText(carA, 40f, vLMedico+600f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+600f,car1a )
        canvas.drawText(carA, 40f, vLMedico+650f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+650f,car1a )
        canvas.drawText(carA, 40f, vLMedico+700f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+700f,car1a )
        canvas.drawText(carA, 40f, vLMedico+750f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+750f,car1a )
        canvas.drawText(carA, 40f, vLMedico+800f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+800f,car1a )
        canvas.drawText(linea2, 40f, vLMedico+850f,lineaDoc2 )
        canvas.drawText(carB, 1337f, vLMedico+850f,car1a )

        /*-------------------------------------*/
        /*              MASCOTA                  */
        /*-------------------------------------*/
        pet.textSize=45f

        pet.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        canvas.drawText("Identificador", 85f, vLMedico+1020f, pet)
        pet.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(petId, 85f, vLMedico+1080f, pet)

        pet.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        canvas.drawText("Mascota", 85f, vLMedico+1150f, pet)
        pet.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(petName, 85f, vLMedico+1210f, pet)

        /*-----------CUADRO-----------*/
        canvas.drawText(lineaMedico, 40f, vLMedico+950f,lineaDoc )
        canvas.drawText(carC, 1337f, vLMedico+950f,lineaDoc )
        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC))
        lineaDoc.textSize = 40f
        canvas.drawText("Mascota", 150f, vLMedico+950f,lineaDoc )
        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        lineaDoc.textSize = 50f
        canvas.drawText(carA, 40f, vLMedico+1000f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1000f,car1a )
        canvas.drawText(carA, 40f, vLMedico+1050f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1050f,car1a )
        canvas.drawText(carA, 40f, vLMedico+1100f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1100f,car1a )
        canvas.drawText(carA, 40f, vLMedico+1150f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1150f,car1a )
        canvas.drawText(carA, 40f, vLMedico+1200f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1200f,car1a )
        canvas.drawText(linea2, 40f, vLMedico+1250f,lineaDoc2 )
        canvas.drawText(carB, 1337f, vLMedico+1250f,car1a )

        /*-------------------------------------*/
        /*              MEDICINA                  */
        /*-------------------------------------*/
        medicine.textSize=45f

        medicine.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        canvas.drawText("Descripción", 85f, vLMedico+1420f, medicine)
        medicine.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(medicineDesc, 85f, vLMedico+1480f, medicine)
        medicine.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

        canvas.drawText("Cantidad", 85f, vLMedico+1550f, medicine)
        medicine.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        canvas.drawText(medicineCant, 85f, vLMedico+1610f, medicine)

        /*-----------CUADRO-----------*/
        canvas.drawText(lineaMedico, 40f, vLMedico+1350f,lineaDoc )
        canvas.drawText(carC, 1337f, vLMedico+1350f,lineaDoc )
        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC))
        lineaDoc.textSize = 40f
        canvas.drawText("Medicina", 150f, vLMedico+1350f,lineaDoc )
        lineaDoc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        lineaDoc.textSize = 50f
        canvas.drawText(carA, 40f, vLMedico+1400f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1400f,car1a )
        canvas.drawText(carA, 40f, vLMedico+1450f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1450f,car1a )
        canvas.drawText(carA, 40f, vLMedico+1500f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1500f,car1a )
        canvas.drawText(carA, 40f, vLMedico+1550f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1550f,car1a )
        canvas.drawText(carA, 40f, vLMedico+1600f,car1a )
        canvas.drawText(carA, 1337f, vLMedico+1600f,car1a )
        canvas.drawText(linea2, 40f, vLMedico+1650f,lineaDoc2 )
        canvas.drawText(carB, 1337f, vLMedico+1650f,car1a )

        pdfDocument.finishPage(pagina1)

        val directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directoryPath, "Archivo.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            pdfDocument.close()
        }
    }
}