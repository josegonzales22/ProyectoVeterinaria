package com.jgonzales.proyectoveterinaria

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.entidades.Medico
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO
import com.jgonzales.proyectoveterinaria.modelo.MascotaDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicinaDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicoDAO

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class FragmentInicio : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView

    lateinit var txtDniCli:TextView
    lateinit var txtNomCli:TextView
    lateinit var txtApeCli:TextView

    lateinit var txtNomMas:TextView
    lateinit var txtRazMas:TextView
    lateinit var txtGenMas:TextView

    lateinit var txtCodMed:TextView
    lateinit var txtCantMed:TextView
    lateinit var txtDesMed:TextView

    lateinit var txtApeMedic:TextView
    lateinit var txtMailMedic:TextView
    lateinit var txtCelMedic:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)
        asignarReferencias(view)
        seccionCliente()
        seccionMascota()
        seccionMedico()
        seccionMedicamento()

        val databundle = arguments
        val medicName=databundle!!.getString("medicName")
        txtMensaje.setText("Hola "+medicName.toString())
        return view
    }
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentInicio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun asignarReferencias(view: View){
        txtMensaje = view.findViewById(R.id.txtMensajeInicio)
        txtDniCli = view.findViewById(R.id.infDniCli)
        txtNomCli = view.findViewById(R.id.infNomCli)
        txtApeCli = view.findViewById(R.id.infApeCli)
        txtNomMas = view.findViewById(R.id.infNombreMas)
        txtRazMas = view.findViewById(R.id.infRazaMas)
        txtGenMas = view.findViewById(R.id.infGeneroMas)
        txtCodMed = view.findViewById(R.id.infCodMed)
        txtCantMed = view.findViewById(R.id.infCantMed)
        txtDesMed = view.findViewById(R.id.infDesMed)
        txtApeMedic = view.findViewById(R.id.infApeMed)
        txtMailMedic = view.findViewById(R.id.infMailMed)
        txtCelMedic = view.findViewById(R.id.infCelMed)
    }
    fun seccionCliente(){
        var cli:ClienteDAO= ClienteDAO(requireActivity())
        var cliente:Cliente= Cliente()
        cliente=cli.obtenerCliente()
        txtDniCli.setText("Dni: "+cliente.dniCliente)
        txtNomCli.setText("Nombre: "+cliente.nombreCliente)
        txtApeCli.setText("Apellido: "+cliente.apellidoCliente)
    }
    fun seccionMascota(){
        var mas:MascotaDAO = MascotaDAO(requireActivity())
        var mascota:Mascota = Mascota()
        mascota=mas.obtenerMascota()
        txtNomMas.setText("Nombre: "+mascota.nombreMascota)
        txtRazMas.setText("Raza: "+mascota.razaMascota)
        txtGenMas.setText("Género: "+mascota.generoMascota)
    }
    fun seccionMedicamento(){
        var med:MedicinaDAO = MedicinaDAO(requireActivity())
        var med2:Medicina = Medicina()
        med2 = med.obtenerMedicina()
        txtCodMed.setText("Código: "+med2.codigoMedicina)
        txtCantMed.setText("Cantidad: "+med2.cantidadMedicina)
        txtDesMed.setText("Descripción: "+med2.descripcionMedicina)
    }
    fun seccionMedico(){
        var medic:MedicoDAO = MedicoDAO(requireActivity())
        var medic2:Medico = Medico()
        medic2 = medic.obtenerMedico()
        txtApeMedic.setText("Apellido: "+medic2.apellidoMedico)
        txtMailMedic.setText("Correo: "+medic2.correoMedico)
        txtCelMedic.setText("Celular: "+medic2.celularMedico)
    }
}