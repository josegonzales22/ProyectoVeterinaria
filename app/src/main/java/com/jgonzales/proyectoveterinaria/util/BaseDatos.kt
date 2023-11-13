package com.jgonzales.proyectoveterinaria.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION

class BaseDatos (context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, VERSION) {
    companion object{
        private const val DATABASE_NAME = "data.db"
        private const val VERSION = 4
    }
    override fun onCreate(db: SQLiteDatabase) {

        val sqlMedicina = "CREATE TABLE IF NOT EXISTS medicinas " +
                "(idMedicina INTEGER PRIMARY KEY AUTOINCREMENT," +
                "codigoMedicina INTEGER NOT NULL UNIQUE," +
                "descripcionMedicina TEXT NOT NULL," +
                "fechaVencimientoMedicina DATE NOT NULL," +
                "cantidadMedicina INTEGER NOT NULL);"

        val sqlMascota="CREATE TABLE IF NOT EXISTS mascotas " +
                "(idMascota INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreMascota TEXT NOT NULL," +
                "especieMascota TEXT NOT NULL," +
                "razaMascota TEXT NOT NULL," +
                "generoMascota TEXT NOT NULL);"

        val sqlCliente = "CREATE TABLE IF NOT EXISTS clientes " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dniCliente INTEGER NOT NULL UNIQUE," +
                "nombreCliente TEXT NOT NULL," +
                "apellidoCliente TEXT NOT NULL," +
                "correoCliente TEXT NOT NULL," +
                "celularCliente INTEGER NOT NULL CHECK(length(celularCliente) = 9) " +
                "CHECK(length(dniCliente) = 8) );"

        val sqlMedico = "CREATE TABLE IF NOT EXISTS medicos " +
                "(idMedico INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dniMedico INTEGER NOT NULL UNIQUE," +
                "nombreMedico TEXT NOT NULL," +
                "apellidoMedico TEXT NOT NULL," +
                "correoMedico TEXT NOT NULL," +
                "celularMedico INTEGER NOT NULL CHECK(length(celularMedico) = 9) " +
                "CHECK(length(dniMedico) = 8) );"

        db.execSQL(sqlMedicina)
        db.execSQL(sqlMascota)
        db.execSQL(sqlCliente)
        db.execSQL(sqlMedico)

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS clientes")
        db.execSQL("DROP TABLE IF EXISTS mascotas")
        db.execSQL("DROP TABLE IF EXISTS medicinas")
        db.execSQL("DROP TABLE IF EXISTS medicos")
        onCreate(db)
    }
}