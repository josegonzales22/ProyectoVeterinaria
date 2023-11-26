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
import com.jgonzales.proyectoveterinaria.dialog.DialogCliente
import com.jgonzales.proyectoveterinaria.dialog.DialogMascota
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO
import com.jgonzales.proyectoveterinaria.modelo.MascotaDAO

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentMascota : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView

    lateinit var txtNomMas:TextView
    lateinit var txtRazMas:TextView
    lateinit var txtGenMas:TextView

    lateinit var btnNuevaMascota: Button
    lateinit var btnListarMascota : Button
    lateinit var btnEditarMascota: Button
    lateinit var btnEliminarMascota: Button
    lateinit var btnGenerarTicketMascota: Button

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
        val view = inflater.inflate(R.layout.fragment_mascota, container, false)
        asignarReferencias(view)
        eventoOnClick()
        seccionMascota()
        val databundle = arguments
        medicName=databundle!!.getString("medicName").toString()
        txtMensaje.setText("Hola "+medicName.toString())

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMascota().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun seccionMascota(){
        var mas: MascotaDAO = MascotaDAO(requireActivity())
        var mascota: Mascota = Mascota()
        mascota=mas.obtenerMascota()
        txtNomMas.setText("Nombre: "+mascota.nombreMascota)
        txtRazMas.setText("Raza: "+mascota.razaMascota)
        txtGenMas.setText("Género: "+mascota.generoMascota)
    }
    fun asignarReferencias(view : View){
        btnNuevaMascota = view.findViewById(R.id.btnNuevoMedico)
        btnListarMascota = view.findViewById(R.id.btnListarMascotas)
        btnEditarMascota = view.findViewById(R.id.btnEditarMedico)
        btnEliminarMascota = view.findViewById(R.id.btnEliminarMedico)
        btnGenerarTicketMascota = view.findViewById(R.id.btnGenerarTicketMascota)
        txtMensaje = view.findViewById(R.id.txtMensaje)
        txtNomMas = view.findViewById(R.id.infNombreMas)
        txtRazMas = view.findViewById(R.id.infRazaMas)
        txtGenMas = view.findViewById(R.id.infGeneroMas)
    }
    fun eventoOnClick(){
        btnNuevaMascota.setOnClickListener{
            val intent = Intent(requireActivity(), NuevaMascota::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("medicName", medicName)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        btnListarMascota.setOnClickListener {
            val intent = Intent(requireActivity(), ListaMascotaActivity::class.java)
            startActivity(intent)
        }
        btnEditarMascota.setOnClickListener {
            DialogMascota(
                onSubmitClickListener = {idMacota ->
                    val mascotaDAO = MascotaDAO(requireActivity())

                    try {
                        // Validar si el cliente con el DNI proporcionado existe
                        val mascotaExistente = mascotaDAO.obtenerMascotaPorId(idMacota.toInt())

                        if (mascotaExistente != null) {
                            // El cliente existe, redirigir a la actividad EditarCliente
                            val intent = Intent(requireActivity(), EditarMascota::class.java)

                            // Pasa los datos del cliente a la actividad de edición
                            intent.putExtra("idMascota", mascotaExistente.idMascota)
                            intent.putExtra("nombreMascota", mascotaExistente.nombreMascota?:"")
                            intent.putExtra("especieMascota", mascotaExistente.especieMascota ?: "")
                            intent.putExtra("razaMascota", mascotaExistente.razaMascota ?: "")
                            intent.putExtra("generoMascota", mascotaExistente.generoMascota ?: "")

                            startActivity(intent)
                        } else {
                            // El cliente no existe, mostrar Toast
                            Toast.makeText(requireContext(), "Mascota con ID $idMacota no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Manejar la excepción, por ejemplo, mostrar un mensaje de error
                        Toast.makeText(requireContext(), "Mascota con ID $idMacota no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            ).show(parentFragmentManager, "dialogMascota")
        }
        btnEliminarMascota.setOnClickListener {
            DialogMascota(
                onSubmitClickListener = {idMascota ->
                    val mascotaDAO = MascotaDAO(requireActivity())
                    val resultado = mascotaDAO.eliminarMascota(idMascota.toInt())

                    Toast.makeText(requireContext(), resultado, Toast.LENGTH_SHORT).show()

                    val intent = Intent(requireContext(), ContenedorActivity::class.java)
                    startActivity(intent)
                }
            ).show(parentFragmentManager, "dialogMascota")
        }
        btnGenerarTicketMascota.setOnClickListener {
            val intent = Intent(requireActivity(), TicketMascota::class.java)
            startActivity(intent)
        }
    }
}