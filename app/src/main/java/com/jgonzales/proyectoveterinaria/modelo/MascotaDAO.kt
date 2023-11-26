package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import android.widget.Toast
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.util.BaseDatos
import kotlin.Exception

class MascotaDAO (context: Context) {
    private var baseDatos:BaseDatos = BaseDatos(context)

    fun registrarMascota(mascota: Mascota):String{
        var respuesta = ""
        val db = baseDatos.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("nombreMascota", mascota.nombreMascota)
            valores.put("especieMascota", mascota.especieMascota)
            valores.put("razaMascota", mascota.razaMascota)
            valores.put("generoMascota", mascota.generoMascota)

            var rpta = db.insert("mascotas",null,valores)
            if(rpta == -1L) {respuesta = "Error al insertar"}
            else{ respuesta = "Se registró correctamente"}

        }catch (e: Exception){
            respuesta = e.message.toString()
        }finally {
            db.close()
        }

        return respuesta
    }
    fun obtenerMascota():Mascota{
        var mas:Mascota = Mascota()
        val db=baseDatos.readableDatabase
        var cr:Cursor=db.rawQuery("SELECT * FROM mascotas ORDER BY idMascota DESC LIMIT 1", null)
        try {
            if(cr!=null&&cr.moveToFirst()){
                do{
                    mas.idMascota=cr.getString(0).toInt()
                    mas.nombreMascota=cr.getString(1).toString()
                    mas.especieMascota=cr.getString(2).toString()
                    mas.razaMascota=cr.getString(3).toString()
                    mas.generoMascota=cr.getString(4).toString()
                }while (cr.moveToNext())
            }else{
                mas.idMascota=0
                mas.nombreMascota="Ninguno"
                mas.especieMascota="Ninguno"
                mas.razaMascota="Ninguno"
                mas.generoMascota="Ninguno"
            }
        }catch (ex:Exception){
        }
        db.close()
        return mas
    }

    fun eliminarMascota(idMascota: Int): String {
        var respuesta = ""
        val db = baseDatos.writableDatabase
        try {
            val resultado = db.delete("mascotas", "idMascota=?", arrayOf(idMascota.toString()))
            if (resultado == 0) {
                respuesta = "Mascota no encontrada"
            } else {
                respuesta = "Mascota eliminada correctamente"
            }
        } catch (e: Exception) {
            respuesta = e.message.toString()
        } finally {
            db.close()
        }
        return respuesta
    }

    fun obtenerMascotaPorId(idMascota: Int): Mascota {
        var masc: Mascota = Mascota()
        val db = baseDatos.readableDatabase
        val cr: Cursor = db.rawQuery("SELECT * FROM mascotas WHERE idMascota = ?", arrayOf(idMascota.toString()))
        try {
            if (cr != null && cr.moveToFirst()) {
                do {
                    masc.idMascota = cr.getString(0).toInt()
                    masc.nombreMascota = cr.getString(1).toString()
                    masc.especieMascota = cr.getString(2).toString()
                    masc.razaMascota = cr.getString(3).toString()
                    masc.generoMascota = cr.getString(4).toString()
                } while (cr.moveToNext())
            } else {
                // Cliente no encontrado, puedes manejar esto de alguna manera
            }
        } catch (ex: Exception) {
        }
        db.close()
        return masc
    }

    fun actualizarMascota(mascota: Mascota): String {
        var respuesta = ""
        val db = baseDatos.writableDatabase

        try {
            val valores = ContentValues().apply {
                put("nombreMascota", mascota.nombreMascota)
                put("especieMascota", mascota.especieMascota)
                put("razaMascota", mascota.razaMascota)
                put("generoMascota", mascota.generoMascota)
            }

            val resultado = db.update("mascotas", valores, "idMascota=?", arrayOf(mascota.idMascota.toString()))

            if (resultado > 0) {
                respuesta = "Mascota actualizada correctamente"
            } else {
                respuesta = "Error al actualizar los datos de la mascota"
            }
        } catch (e: Exception) {
            respuesta = "Datos ingresados incorrectamente"
        } finally {
            db.close()
        }

        return respuesta
    }
    fun cargarMascotas():ArrayList<Mascota>{
        val listaMascota:ArrayList<Mascota> = ArrayList()
        val query = "SELECT * FROM mascotas"
        val db = baseDatos.readableDatabase
        val cursor:Cursor

        try{
            cursor = db.rawQuery(query, null)
            if(cursor.count>0){
                cursor.moveToFirst()
                do{
                    val id:Int = cursor.getInt(cursor.getColumnIndexOrThrow("idMascota"))
                    val nombreMascota:String = cursor.getString(cursor.getColumnIndexOrThrow("nombreMascota"))
                    val especieMascota:String = cursor.getString(cursor.getColumnIndexOrThrow("especieMascota"))
                    val razaMascota:String = cursor.getString(cursor.getColumnIndexOrThrow("razaMascota"))
                    val generoMascota:String = cursor.getString(cursor.getColumnIndexOrThrow("generoMascota"))

                    val mascota = Mascota()
                    mascota.idMascota = id
                    mascota.nombreMascota = nombreMascota
                    mascota.especieMascota = especieMascota
                    mascota.razaMascota = razaMascota
                    mascota.generoMascota = generoMascota
                    listaMascota.add(mascota)
                }while (cursor.moveToNext())
            }
        }catch (ex:Exception){
            Toast.makeText(null, ex.message, Toast.LENGTH_SHORT).show()
        }finally {
            db.close()
        }
        return listaMascota
    }
}