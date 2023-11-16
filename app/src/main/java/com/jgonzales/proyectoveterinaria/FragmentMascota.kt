package com.jgonzales.proyectoveterinaria

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.jgonzales.proyectoveterinaria.entidades.Mascota
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
        btnEditarMascota.setOnClickListener {
            val intent = Intent(requireActivity(), EditarMascota::class.java)
            startActivity(intent)
        }
        btnEliminarMascota.setOnClickListener {
            val intent = Intent(requireActivity(), EliminarMascota::class.java)
            startActivity(intent)
        }
        btnGenerarTicketMascota.setOnClickListener {
            val intent = Intent(requireActivity(), TicketMascota::class.java)
            startActivity(intent)
        }
    }
}