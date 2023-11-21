package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.util.BaseDatos
import kotlin.Exception

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
    fun obtenerCliente():Cliente{
        var cli : Cliente=Cliente()
        val db=baseDatos.readableDatabase
        var cr:Cursor=db.rawQuery("SELECT * FROM clientes ORDER BY id DESC LIMIT 1", null)
        try {
            if(cr!=null&&cr.moveToFirst()){
                do{
                    cli.id=cr.getString(0).toInt()
                    cli.dniCliente=cr.getString(1).toInt()
                    cli.nombreCliente=cr.getString(2).toString()
                    cli.apellidoCliente=cr.getString(3).toString()
                    cli.correoCliente=cr.getString(4).toString()
                    cli.celularCliente=cr.getString(5).toInt()
                }while (cr.moveToNext())
            }else{
                cli.id=0
                cli.dniCliente=88888888
                cli.nombreCliente="Ninguno"
                cli.apellidoCliente="Ninguno"
                cli.correoCliente="Ninguno"
                cli.celularCliente=999999999
            }
        }catch (ex:Exception){
        }
        db.close()
        return cli
    }
    fun eliminarCliente(dniCliente: Int): String {
        var respuesta = ""
        val db = baseDatos.writableDatabase
        try {
            val resultado = db.delete("clientes", "dniCliente=?", arrayOf(dniCliente.toString()))
            if (resultado == 0) {
                respuesta = "Cliente no encontrado"
            } else {
                respuesta = "Cliente eliminado correctamente"
            }
        } catch (e: Exception) {
            respuesta = e.message.toString()
        } finally {
            db.close()
        }
        return respuesta
    }

    fun obtenerClientePorDni(dniCliente: Int): Cliente {
        var cli: Cliente = Cliente()
        val db = baseDatos.readableDatabase
        val cr: Cursor = db.rawQuery("SELECT * FROM clientes WHERE dniCliente = ?", arrayOf(dniCliente.toString()))
        try {
            if (cr != null && cr.moveToFirst()) {
                do {
                    cli.id = cr.getString(0).toInt()
                    cli.dniCliente = cr.getString(1).toInt()
                    cli.nombreCliente = cr.getString(2).toString()
                    cli.apellidoCliente = cr.getString(3).toString()
                    cli.correoCliente = cr.getString(4).toString()
                    cli.celularCliente = cr.getString(5).toInt()
                } while (cr.moveToNext())
            } else {
                // Cliente no encontrado, puedes manejar esto de alguna manera
            }
        } catch (ex: Exception) {
        }
        db.close()
        return cli
    }

    fun actualizarCliente(cliente: Cliente): String {
        var respuesta = ""
        val db = baseDatos.writableDatabase

        try {
            val valores = ContentValues().apply {
                put("nombreCliente", cliente.nombreCliente)
                put("apellidoCliente", cliente.apellidoCliente)
                put("correoCliente", cliente.correoCliente)
                put("celularCliente", cliente.celularCliente)
            }

            val resultado = db.update("clientes", valores, "dniCliente=?", arrayOf(cliente.dniCliente.toString()))

            if (resultado > 0) {
                respuesta = "Cliente actualizado correctamente"
            } else {
                respuesta = "Error al actualizar el cliente"
            }
        } catch (e: Exception) {
            respuesta = "Datos ingresados incorrectamente"
        } finally {
            db.close()
        }

        return respuesta
    }


}