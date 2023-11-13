package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.util.BaseDatos
import java.lang.Exception
import java.text.SimpleDateFormat

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
}