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
                onSubmitClickListener = {
                    /*
                    * it = dni de medico
                    * validar si el $it existe en la tabla medicos
                    *
                    * Caso si:
                    *   Redirigir al activity editar médico y llenar el formululario por defecto
                    *   con los datos del médico encontrado
                    * Caso no:
                    *   Mostrar un toast indicando que el médico no existe
                    * */
                    Toast.makeText(requireContext(), "Médico $it encontrada", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireActivity(), EditarMedico::class.java)
                    startActivity(intent)
                }
            ).show(parentFragmentManager, "dialogMedico")
        }
        btnEliminarMedico.setOnClickListener {
            DialogMedico(
                onSubmitClickListener = {
                    /*
                    * it = id del médico
                    * validar si el $it existe en la tabla médicos
                    *
                    * Caso si:
                    *   Eliminar el médico de la base de datos y mostrar un toast indicando
                    *   la operación
                    * Caso no:
                    *   Mostrar un toast indicando que el médico no existe
                    * */
                    Toast.makeText(requireContext(), "Médico $it encontrada", Toast.LENGTH_SHORT).show()
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