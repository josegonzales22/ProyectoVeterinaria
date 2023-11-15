package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.util.BaseDatos
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class MedicinaDAO (context: Context){
    private var baseDatos: BaseDatos = BaseDatos(context)

    fun registrarMedicina(medicina: Medicina):String{
        var respuesta = ""
        val db = baseDatos.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("codigoMedicina", medicina.codigoMedicina)
            valores.put("descripcionMedicina", medicina.descripcionMedicina)

            // Formatear la fecha como cadena
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val fechaVencimiento = dateFormat.format(medicina.fechaVencimientoMedicina)
            valores.put("fechaVencimientoMedicina", fechaVencimiento)

            valores.put("cantidadMedicina", medicina.cantidadMedicina)

            var rpta = db.insert("medicinas",null,valores)
            if(rpta == -1L) {respuesta = "Error al insertar"}
            else{ respuesta = "Se registró correctamente"}

        }catch (e: Exception){
            respuesta = e.message.toString()
        }finally {
            db.close()
        }
        return respuesta
    }
    fun obtenerMedicina():Medicina{
        var medicina:Medicina= Medicina()
        val db=baseDatos.readableDatabase
        var fecha:String
        val pattern = "dd-MM-yyyy"

        var cr: Cursor =db.rawQuery("SELECT * FROM medicinas ORDER BY idMedicina DESC LIMIT 1", null)
        try {
            if(cr!=null&&cr.moveToFirst()){
                do{
                    medicina.idMedicina=cr.getString(0).toInt()
                    medicina.codigoMedicina=cr.getString(1).toString()
                    medicina.descripcionMedicina=cr.getString(2).toString()
                    fecha=cr.getString(3).toString()
                    val dateFormatter = DateTimeFormatter.ofPattern(pattern)
                    val date = LocalDate.parse(fecha, dateFormatter)
                    val date2 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
                    medicina.fechaVencimientoMedicina=date2
                    medicina.cantidadMedicina = cr.getString(4).toInt()
                }while (cr.moveToNext())
            }
        }catch (ex:Exception){
        }
        db.close()
        return medicina
    }
}