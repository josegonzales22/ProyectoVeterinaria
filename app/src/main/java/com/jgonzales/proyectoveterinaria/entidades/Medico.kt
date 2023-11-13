package com.jgonzales.proyectoveterinaria.entidades

import android.annotation.SuppressLint

class Medico {
    var idMedico:Int = 0
    lateinit var dniMedico:String
    lateinit var contrasenia:String
    lateinit var nombreMedico:String
    lateinit var apellidoMedico:String
    lateinit var correoMedico:String
    lateinit var celularMedico:String


    @SuppressLint("NotConstructor")
    fun Medico(dniMedico:String, contrasenia:String, nombreMedico:String, apellidoMedico:String,
               correoMedico:String, celularMedico:String){
        this.dniMedico=dniMedico
        this.contrasenia=contrasenia
        this.nombreMedico=nombreMedico
        this.apellidoMedico=apellidoMedico
        this.correoMedico=correoMedico
        this.celularMedico=celularMedico
    }

    fun isNull(): Boolean {
        return !(dniMedico.isEmpty()&&contrasenia.isEmpty()&&nombreMedico.isEmpty()&&
                apellidoMedico.isEmpty()&&correoMedico.isEmpty()&&celularMedico.isEmpty())
    }
}