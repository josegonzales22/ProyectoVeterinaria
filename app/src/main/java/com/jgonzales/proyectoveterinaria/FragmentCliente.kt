package com.jgonzales.proyectoveterinaria

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class FragmentCliente : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView

    lateinit var btnNuevoCliente: Button
    lateinit var btnEditarCliente: Button
    lateinit var btnEliminarCliente: Button

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cliente, container, false)
        asignarRerefencias(view)
        eventoOnClick()

        seccionCliente()

        val databundle = arguments
        val medicName=databundle!!.getString("medicName")
        txtMensaje.setText("Hola "+medicName.toString())
        return view

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentCliente().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun asignarRerefencias(view : View){
        btnNuevoCliente = view.findViewById(R.id.btnNuevoMedico)
        btnEditarCliente = view.findViewById(R.id.btnEditarMedico)
        btnEliminarCliente = view.findViewById(R.id.btnEliminarMedico)
        txtMensaje = view.findViewById(R.id.txtMensaje)

        txtDniCli = view.findViewById(R.id.infDniCli)
        txtNomCli = view.findViewById(R.id.infNomCli)
        txtApeCli = view.findViewById(R.id.infApeCli)
    }
    fun eventoOnClick(){
        btnNuevoCliente.setOnClickListener{
            val intent = Intent(requireActivity(), activity_nuevo_cliente::class.java)
            startActivity(intent)
        }
        btnEditarCliente.setOnClickListener {
            val intent = Intent(requireActivity(), EditarCliente::class.java)
            startActivity(intent)
        }
        btnEliminarCliente.setOnClickListener {
            val intent = Intent(requireActivity(), EliminarCliente::class.java)
            startActivity(intent)
        }
    }
    fun seccionCliente(){
        var cli: ClienteDAO = ClienteDAO(requireActivity())
        var cliente: Cliente = Cliente()
        cliente=cli.obtenerCliente()
        txtDniCli.setText("Dni: "+cliente.dniCliente)
        txtNomCli.setText("Nombre: "+cliente.nombreCliente)
        txtApeCli.setText("Apellido: "+cliente.apellidoCliente)
    }
}