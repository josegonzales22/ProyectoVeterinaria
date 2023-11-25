package com.jgonzales.proyectoveterinaria

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jgonzales.proyectoveterinaria.databinding.FragmentClienteBinding
import com.jgonzales.proyectoveterinaria.dialog.DialogCliente
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class FragmentCliente : Fragment() {
    private lateinit var binding:FragmentClienteBinding
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView

    lateinit var btnNuevoCliente: Button
    lateinit var btnListarClientes : Button
    lateinit var btnEditarCliente: Button
    lateinit var btnEliminarCliente: Button

    lateinit var txtDniCli:TextView
    lateinit var txtNomCli:TextView
    lateinit var txtApeCli:TextView
    lateinit var medicName:String


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
        val view = inflater.inflate(R.layout.fragment_cliente, container, false)
        asignarRerefencias(view)
        eventoOnClick()
        seccionCliente()

        val databundle = arguments
        medicName=databundle!!.getString("medicName").toString()
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
        btnListarClientes = view.findViewById(R.id.btnListarClientes)
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
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        btnListarClientes.setOnClickListener {
            val intent = Intent(requireActivity(), ListaClientesActivity::class.java)
            startActivity(intent)
        }
        btnEditarCliente.setOnClickListener {
            DialogCliente(
                onSubmitClickListener = {dniCliente ->
                    val clienteDAO = ClienteDAO(requireActivity())

                    try {
                        // Validar si el cliente con el DNI proporcionado existe
                        val clienteExistente = clienteDAO.obtenerClientePorDni(dniCliente.toInt())

                        if (clienteExistente != null) {
                            // El cliente existe, redirigir a la actividad EditarCliente
                            val intent = Intent(requireActivity(), EditarCliente::class.java)

                            // Pasa los datos del cliente a la actividad de edición
                            intent.putExtra("dniCliente", clienteExistente.dniCliente)
                            intent.putExtra("nombreCliente", clienteExistente.nombreCliente ?: "")
                            intent.putExtra("apellidoCliente", clienteExistente.apellidoCliente ?: "")
                            intent.putExtra("correoCliente", clienteExistente.correoCliente ?: "")
                            intent.putExtra("celularCliente", clienteExistente.celularCliente)

                            startActivity(intent)
                        } else {
                            // El cliente no existe, mostrar Toast
                            Toast.makeText(requireContext(), "Cliente con DNI $dniCliente no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Manejar la excepción, por ejemplo, mostrar un mensaje de error
                        Toast.makeText(requireContext(), "Cliente con DNI $dniCliente no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            ).show(parentFragmentManager, "dialogCliente")
        }
        btnEliminarCliente.setOnClickListener {
            DialogCliente(
                onSubmitClickListener = { dniCliente ->
                    val clienteDAO = ClienteDAO(requireActivity())
                    val resultado = clienteDAO.eliminarCliente(dniCliente.toInt())

                    Toast.makeText(requireContext(), resultado, Toast.LENGTH_SHORT).show()

                    // Después de mostrar el Toast, inicia la actividad
                    val intent = Intent(requireContext(), ContenedorActivity::class.java)
                    startActivity(intent)

                }
            ).show(parentFragmentManager, "dialogCliente")
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

private fun Intent.putExtra(s: String, cliente: Cliente) {

}
