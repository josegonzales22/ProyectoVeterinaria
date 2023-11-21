package com.jgonzales.proyectoveterinaria.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.jgonzales.proyectoveterinaria.entidades.Medico
import com.jgonzales.proyectoveterinaria.util.BaseDatos
import java.lang.Exception

class MedicoDAO (context: Context) {
    private var baseDatos: BaseDatos = BaseDatos(context)

    fun registrarMedico(medico: Medico):String{
        var respuesta = ""
        val db = baseDatos.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("dniMedico", medico.dniMedico)
            valores.put("contrasenia", medico.contrasenia)
            valores.put("nombreMedico", medico.nombreMedico)
            valores.put("apellidoMedico", medico.apellidoMedico)
            valores.put("correoMedico", medico.correoMedico)
            valores.put("celularMedico", medico.celularMedico)
            var rpta = db.insert("medicos",null,valores)
            if(rpta == -1L) {respuesta = "Error al insertar"}
            else{ respuesta = "Se registró correctamente"}

        }catch (e: Exception){
            respuesta = e.message.toString()
        }finally {
            db.close()
        }

        return respuesta
    }
    fun login(u:String, p:String):Int{
        var a:Int = 0
        val db=baseDatos.readableDatabase
        var cr:Cursor=db.rawQuery("SELECT * FROM medicos", null)
        if(cr!=null&&cr.moveToFirst()){
            do{
                if(cr.getString(1).equals(u) && cr.getString(2).equals(p)){
                    a++
                }
            }while (cr.moveToNext())
        }
        return a
    }
    fun getNombreMedico(u:String): String? {
        val db=baseDatos.readableDatabase
        var cr:Cursor=db.rawQuery("SELECT * FROM medicos", null)
        if(cr!=null&&cr.moveToFirst()){
            do{
                if(cr.getString(1).equals(u)){
                    return cr.getString(3)
                }
            }while (cr.moveToNext())
        }
        return null
    }
    fun obtenerMedico():Medico{
        var medico:Medico = Medico()
        val db=baseDatos.readableDatabase
        var cr:Cursor=db.rawQuery("SELECT * FROM medicos ORDER BY idMedico DESC LIMIT 1", null)
        try {
            if(cr!=null&&cr.moveToFirst()){
                do{
                    medico.idMedico=cr.getString(0).toInt()
                    medico.dniMedico=cr.getString(1).toString()
                    medico.contrasenia=cr.getString(2).toString()
                    medico.nombreMedico=cr.getString(3).toString()
                    medico.apellidoMedico=cr.getString(4).toString()
                    medico.correoMedico=cr.getString(5).toString()
                    medico.celularMedico=cr.getString(6).toString()
                }while (cr.moveToNext())
            }else{
                medico.idMedico=0
                medico.dniMedico="Ninguno"
                medico.contrasenia="Ninguno"
                medico.nombreMedico="Ninguno"
                medico.apellidoMedico="Ninguno"
                medico.correoMedico="Ninguno"
                medico.celularMedico="Ninguno"
            }
        }catch (ex:Exception){
        }
        db.close()
        return medico
    }

    fun eliminarMedico(dniMedico: Int): String {
        var respuesta = ""
        val db = baseDatos.writableDatabase
        try {
            val resultado = db.delete("medicos", "dniMedico=?", arrayOf(dniMedico.toString()))
            if (resultado == 0) {
                respuesta = "Medico no encontrado"
            } else {
                respuesta = "Medico eliminado correctamente"
            }
        } catch (e: Exception) {
            respuesta = e.message.toString()
        } finally {
            db.close()
        }
        return respuesta
    }

    fun obtenerMedicoPorDni(dniMedico: Int): Medico {
        var med: Medico = Medico()
        val db = baseDatos.readableDatabase
        val cr: Cursor = db.rawQuery("SELECT * FROM medicos WHERE dniMedico = ?", arrayOf(dniMedico.toString()))
        try {
            if (cr != null && cr.moveToFirst()) {
                do {
                    med.idMedico = cr.getString(0).toInt()
                    med.dniMedico = cr.getString(1).toString()
                    med.nombreMedico = cr.getString(3).toString()
                    med.apellidoMedico = cr.getString(4).toString()
                    med.correoMedico = cr.getString(5).toString()
                    med.celularMedico = cr.getString(6).toString()
                } while (cr.moveToNext())
            } else {
                // Cliente no encontrado, puedes manejar esto de alguna manera
            }
        } catch (ex: Exception) {
        }
        db.close()
        return med
    }

    fun actualizarMedico(medico: Medico): String {
        var respuesta = ""
        val db = baseDatos.writableDatabase

        try {
            val valores = ContentValues().apply {
                put("nombreMedico", medico.nombreMedico)
                put("apellidoMedico", medico.apellidoMedico)
                put("correoMedico", medico.correoMedico)
                put("celularMedico", medico.celularMedico)
            }

            val resultado = db.update("medicos", valores, "dniMedico=?", arrayOf(medico.dniMedico.toString()))

            if (resultado > 0) {
                respuesta = "Medico actualizado correctamente"
            } else {
                respuesta = "Error al actualizar el medico"
            }
        } catch (e: Exception) {
            respuesta = "Datos ingresados incorrectamente"
        } finally {
            db.close()
        }

        return respuesta
    }
}