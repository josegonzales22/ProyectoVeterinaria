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
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class FragmentInicio : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView
    lateinit var txtDniCli:TextView
    lateinit var txtNomCli:TextView
    lateinit var txtApeCli:TextView

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

    }
    fun seccionMedicamento(){

    }
    fun seccionMedico(){

    }
}