package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.jgonzales.proyectoveterinaria.R
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.util.BaseDatos
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.Exception

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
    fun cargarMedicinas():ArrayList<Medicina>{
        val listaMedicina:ArrayList<Medicina> = ArrayList()
        val query = "SELECT * FROM medicinas"
        val db = baseDatos.readableDatabase
        val cursor:Cursor

        try{
            cursor = db.rawQuery(query, null)
            if(cursor.count>0){
                cursor.moveToFirst()
                do{
                    val id:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idMedicina"))
                    val codigoMedicina:String = cursor.getString(cursor.getColumnIndexOrThrow("codigoMedicina"))
                    val descripcionMedicina:String = cursor.getString(cursor.getColumnIndexOrThrow("descripcionMedicina"))
                    //val fechaVMedicina = convertirStringADate(cursor.getString(cursor.getColumnIndexOrThrow("fechaVencimientoMedicina")))
                    val fechaVMedicina:String = cursor.getString(cursor.getColumnIndexOrThrow("fechaVencimientoMedicina"))
                    val cantidadMedicina:Int = cursor.getInt(cursor.getColumnIndexOrThrow("cantidadMedicina"))

                    val medicina = Medicina()
                    medicina.idMedicina = id
                    medicina.codigoMedicina = codigoMedicina
                    medicina.descripcionMedicina = descripcionMedicina
                    medicina.fechaVencimientoMedicina = convertirStringADate(fechaVMedicina)
                    medicina.cantidadMedicina = cantidadMedicina
                    listaMedicina.add(medicina)
                }while (cursor.moveToNext())
            }
        }catch (ex:Exception){
            Toast.makeText(null, ex.message, Toast.LENGTH_SHORT).show()
        }finally {
            db.close()
        }
        return listaMedicina
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
}