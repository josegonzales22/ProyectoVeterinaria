package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
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
}