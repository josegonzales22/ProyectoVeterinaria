package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.util.BaseDatos
import java.lang.Exception

class ClienteDAO (context:Context) {
    private var baseDatos:BaseDatos = BaseDatos(context)

    fun registrarCliente(cliente: Cliente):String{
        var respuesta = ""
        val db = baseDatos.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("dniCliente", cliente.dniCliente)
            valores.put("nombreCliente", cliente.nombreCliente)
            valores.put("apellidoCliente", cliente.apellidoCliente)
            valores.put("correoCliente", cliente.correoCliente)
            valores.put("celularCliente", cliente.celularCliente)

            var rpta = db.insert("clientes",null,valores)
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