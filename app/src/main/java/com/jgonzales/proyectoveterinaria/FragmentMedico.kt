package com.jgonzales.proyectoveterinaria

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.jgonzales.proyectoveterinaria.dialog.DialogMedicina
import com.jgonzales.proyectoveterinaria.dialog.DialogMedico
import com.jgonzales.proyectoveterinaria.entidades.Medico
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicoDAO

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentMedico : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView

    lateinit var btnNuevoMedico: Button
    lateinit var btnEditarMedico: Button
    lateinit var btnEliminarMedico: Button

    lateinit var txtApeMedic:TextView
    lateinit var txtMailMedic:TextView
    lateinit var txtCelMedic:TextView
    var medicName:String = ""

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
        val view = inflater.inflate(R.layout.fragment_medico, container, false)
        asignarReferencias(view)
        seccionMedico()
        eventoOnClick()
        val databundle = arguments
        medicName=databundle!!.getString("medicName").toString()
        txtMensaje.setText("Hola "+medicName.toString())
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMedico().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun asignarReferencias(view : View){
        btnNuevoMedico = view.findViewById(R.id.btnNuevoMedico)
        btnEditarMedico = view.findViewById(R.id.btnEditarMedico)
        btnEliminarMedico = view.findViewById(R.id.btnEliminarMedico)
        txtMensaje = view.findViewById(R.id.txtMensaje)
        txtApeMedic = view.findViewById(R.id.infApeMed)
        txtMailMedic = view.findViewById(R.id.infMailMed)
        txtCelMedic = view.findViewById(R.id.infCelMed)
    }
    fun eventoOnClick(){
        btnNuevoMedico.setOnClickListener{
            val intent = Intent(requireActivity(), NuevoMedico::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        btnEditarMedico.setOnClickListener {
            DialogMedico(
                onSubmitClickListener = {dniMedico ->
                    val medicoDAO = MedicoDAO(requireActivity())

                    try {
                        // Validar si el cliente con el DNI proporcionado existe
                        val medicoExistente = medicoDAO.obtenerMedicoPorDni(dniMedico.toInt())

                        if (medicoExistente != null) {
                            // El cliente existe, redirigir a la actividad EditarCliente
                            val intent = Intent(requireActivity(), EditarMedico::class.java)

                            // Pasa los datos del cliente a la actividad de edición
                            intent.putExtra("dniMedico", medicoExistente.dniMedico)
                            intent.putExtra("nombreMedico", medicoExistente.nombreMedico ?: "")
                            intent.putExtra("apellidoMedico", medicoExistente.apellidoMedico ?: "")
                            intent.putExtra("correoMedico", medicoExistente.correoMedico ?: "")
                            intent.putExtra("celularMedico", medicoExistente.celularMedico)

                            startActivity(intent)
                        } else {
                            // El cliente no existe, mostrar Toast
                            Toast.makeText(requireContext(), "Medico con DNI $dniMedico no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Manejar la excepción, por ejemplo, mostrar un mensaje de error
                        Toast.makeText(requireContext(), "Medico con DNI $dniMedico no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            ).show(parentFragmentManager, "dialogMedico")
        }
        btnEliminarMedico.setOnClickListener {
            DialogMedico(
                onSubmitClickListener = {dniMedico ->
                    val medicoDAO = MedicoDAO(requireActivity())
                    val resultado = medicoDAO.eliminarMedico(dniMedico.toInt())

                    Toast.makeText(requireContext(), resultado, Toast.LENGTH_SHORT).show()

                    val intent = Intent(requireContext(), ContenedorActivity::class.java)
                    startActivity(intent)
                }
            ).show(parentFragmentManager, "dialogMedico")
        }
    }
    fun seccionMedico(){
        var medic: MedicoDAO = MedicoDAO(requireActivity())
        var medic2: Medico = Medico()
        medic2 = medic.obtenerMedico()
        txtApeMedic.setText("Apellido: "+medic2.apellidoMedico)
        txtMailMedic.setText("Correo: "+medic2.correoMedico)
        txtCelMedic.setText("Celular: "+medic2.celularMedico)
    }
}