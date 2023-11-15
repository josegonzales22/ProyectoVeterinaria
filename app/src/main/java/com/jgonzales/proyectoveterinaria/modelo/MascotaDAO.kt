package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.util.BaseDatos
import java.lang.Exception

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
}