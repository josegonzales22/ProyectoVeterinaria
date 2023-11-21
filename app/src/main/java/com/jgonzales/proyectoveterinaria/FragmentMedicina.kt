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
import com.jgonzales.proyectoveterinaria.dialog.DialogMascota
import com.jgonzales.proyectoveterinaria.dialog.DialogMedicina
import com.jgonzales.proyectoveterinaria.entidades.Medicina
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO
import com.jgonzales.proyectoveterinaria.modelo.MedicinaDAO
import java.text.SimpleDateFormat
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FragmentMedicina : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView

    lateinit var btnNuevaMedicina: Button
    lateinit var btnEditarMedicina: Button
    lateinit var btnEliminarMedicina: Button

    lateinit var txtCodMed:TextView
    lateinit var txtCantMed:TextView
    lateinit var txtDesMed:TextView

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
        val view =  inflater.inflate(R.layout.fragment_medicina, container, false)
        asignarReferencias(view)
        eventoOnClick()
        seccionMedicamento()
        val databundle = arguments
        medicName=databundle!!.getString("medicName").toString()
        txtMensaje.setText("Hola "+medicName.toString())
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMedicina().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun seccionMedicamento(){
        var med: MedicinaDAO = MedicinaDAO(requireActivity())
        var med2: Medicina = Medicina()
        med2 = med.obtenerMedicina()
        txtCodMed.setText("Código: "+med2.codigoMedicina)
        txtCantMed.setText("Cantidad: "+med2.cantidadMedicina)
        txtDesMed.setText("Descripción: "+med2.descripcionMedicina)
    }
    fun asignarReferencias(view : View){
        btnNuevaMedicina = view.findViewById(R.id.btnNuevoMedicamento)
        btnEditarMedicina = view.findViewById(R.id.btnEditarMedicamento)
        btnEliminarMedicina = view.findViewById(R.id.btnEliminarMedicamento)
        txtMensaje = view.findViewById(R.id.txtMensaje)
        txtCodMed = view.findViewById(R.id.infCodMed)
        txtCantMed = view.findViewById(R.id.infCantMed)
        txtDesMed = view.findViewById(R.id.infDesMed)
    }
    fun eventoOnClick(){
        btnNuevaMedicina.setOnClickListener{
            val intent = Intent(requireActivity(), NuevaMedicina::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        btnEditarMedicina.setOnClickListener {
            DialogMedicina(
                onSubmitClickListener = {idMedicina ->
                    val medicinaDAO = MedicinaDAO(requireActivity())

                    try {
                        // Validar si el cliente con el DNI proporcionado existe
                        val medicinaExistente = medicinaDAO.obtenerClientePorId(idMedicina.toInt())

                        if (medicinaExistente != null) {
                            // El cliente existe, redirigir a la actividad EditarCliente
                            val intent = Intent(requireActivity(), EditarMedicina::class.java)

                            // Pasa los datos del cliente a la actividad de edición
                            intent.putExtra("idMedicina", medicinaExistente.idMedicina)
                            intent.putExtra("codigoMedicina", medicinaExistente.codigoMedicina?: "")
                            intent.putExtra("descripcionMedicina", medicinaExistente.descripcionMedicina ?: "")

                            // Suponiendo que medicinaExistente.fechaVencimientoMedicina es un objeto Date
                            intent.putExtra("fechaVencimientoMedicina", SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(medicinaExistente.fechaVencimientoMedicina))


                            intent.putExtra("cantidadMedicina", medicinaExistente.cantidadMedicina)

                            startActivity(intent)
                        } else {
                            // El cliente no existe, mostrar Toast
                            Toast.makeText(requireContext(), "Medicina con ID $idMedicina no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Manejar la excepción, por ejemplo, mostrar un mensaje de error
                        Toast.makeText(requireContext(), "Medicina con I $idMedicina no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            ).show(parentFragmentManager, "dialogMedicina")
        }
        btnEliminarMedicina.setOnClickListener {
            DialogMedicina(
                onSubmitClickListener = {idMedicina ->
                    val medicinaDAO = MedicinaDAO(requireActivity())
                    val resultado = medicinaDAO.eliminarMedicina(idMedicina.toInt())

                    Toast.makeText(requireContext(), resultado, Toast.LENGTH_SHORT).show()

                    val intent = Intent(requireContext(), ContenedorActivity::class.java)
                    startActivity(intent)
                }
            ).show(parentFragmentManager, "dialogMedicina")
        }
    }
}