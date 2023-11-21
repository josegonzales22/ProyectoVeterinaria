package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.jgonzales.proyectoveterinaria.entidades.Cliente
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
            }else{
                medicina.idMedicina=0
                medicina.codigoMedicina="Ninguno"
                medicina.descripcionMedicina="Ninguno"
                fecha="01-01-2000"
                val dateFormatter = DateTimeFormatter.ofPattern(pattern)
                val date = LocalDate.parse(fecha, dateFormatter)
                val date2 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
                medicina.fechaVencimientoMedicina=date2
                medicina.cantidadMedicina = 0
            }
        }catch (ex:Exception){
        }
        db.close()
        return medicina
    }

    fun eliminarMedicina(idMedicina: Int): String {
        var respuesta = ""
        val db = baseDatos.writableDatabase
        try {
            val resultado = db.delete("medicinas", "idMedicina=?", arrayOf(idMedicina.toString()))
            if (resultado == 0) {
                respuesta = "Medicina no encontrada"
            } else {
                respuesta = "Medicina eliminada correctamente"
            }
        } catch (e: Exception) {
            respuesta = e.message.toString()
        } finally {
            db.close()
        }
        return respuesta
    }


    fun obtenerClientePorId(idMedicina: Int): Medicina {
        var medi: Medicina = Medicina()
        val db = baseDatos.readableDatabase
        val cr: Cursor = db.rawQuery("SELECT * FROM medicinas WHERE idMedicina = ?", arrayOf(idMedicina.toString()))
        try {
            if (cr != null && cr.moveToFirst()) {
                do {
                    medi.idMedicina = cr.getString(0).toInt()
                    medi.codigoMedicina = cr.getString(1).toString()
                    medi.descripcionMedicina = cr.getString(2).toString()

                    // Convertir la cadena de fecha a Date
                    val pattern = "dd-MM-yyyy"
                    val dateFormat = SimpleDateFormat(pattern)
                    val fecha = cr.getString(3).toString()
                    val date = dateFormat.parse(fecha)
                    medi.fechaVencimientoMedicina = date

                    medi.cantidadMedicina = cr.getString(4).toInt()
                } while (cr.moveToNext())
            } else {
                // Cliente no encontrado, puedes manejar esto de alguna manera
            }
        } catch (ex: Exception) {
        }
        db.close()
        return medi
    }

    fun actualizarMedicina(medicina: Medicina): String {
        var respuesta = ""
        val db = baseDatos.writableDatabase

        try {
            val valores = ContentValues().apply {
                put("codigoMedicina", medicina.codigoMedicina)
                put("descripcionMedicina", medicina.descripcionMedicina)

                // Formatear la fecha como cadena
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val fechaVencimiento = dateFormat.format(medicina.fechaVencimientoMedicina)
                put("fechaVencimientoMedicina", fechaVencimiento)

                put("cantidadMedicina", medicina.cantidadMedicina)
            }

            val resultado = db.update("medicinas", valores, "idMedicina=?", arrayOf(medicina.idMedicina.toString()))

            if (resultado > 0) {
                respuesta = "Medicina actualizado correctamente"
            } else {
                respuesta = "Error al actualizar el medicamento"
            }
        } catch (e: Exception) {
            respuesta = "Datos ingresados incorrectamente"
        } finally {
            db.close()
        }

        return respuesta
    }
}