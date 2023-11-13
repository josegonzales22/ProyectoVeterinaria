package com.jgonzales.proyectoveterinaria.entidades

import java.util.Date

class Medicina {
    var idMedicina: Int = 0
    lateinit var codigoMedicina:String
    lateinit var descripcionMedicina:String
    lateinit var fechaVencimientoMedicina:Date
    var cantidadMedicina:Int = 0
}