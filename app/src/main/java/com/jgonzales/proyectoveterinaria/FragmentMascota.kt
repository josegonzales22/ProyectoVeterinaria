package com.jgonzales.proyectoveterinaria

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMascota.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMascota : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView

    lateinit var btnNuevaMascota: Button
    lateinit var btnEditarMascota: Button
    lateinit var btnEliminarMascota: Button
    lateinit var btnGenerarTicketMascota: Button

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
        val view = inflater.inflate(R.layout.fragment_mascota, container, false)

        btnNuevaMascota = view.findViewById(R.id.btnNuevaMascota)
        btnNuevaMascota.setOnClickListener{
            val intent = Intent(requireActivity(), NuevaMascota::class.java)
            startActivity(intent)
        }

        btnEditarMascota = view.findViewById(R.id.btnEditarMascota)
        btnEditarMascota.setOnClickListener {
            val intent = Intent(requireActivity(), EditarMascota::class.java)
            startActivity(intent)
        }

        btnEliminarMascota = view.findViewById(R.id.btnEliminarMascota)
        btnEliminarMascota.setOnClickListener {
            val intent = Intent(requireActivity(), EliminarMascota::class.java)
            startActivity(intent)
        }

        btnGenerarTicketMascota = view.findViewById(R.id.btnGenerarTicketMascota)
        btnGenerarTicketMascota.setOnClickListener {
            val intent = Intent(requireActivity(), TicketMascota::class.java)
            startActivity(intent)
        }

        txtMensaje = view.findViewById(R.id.txtMensaje)
        val databundle = arguments
        val medicName=databundle!!.getString("medicName")
        txtMensaje.setText("Hola "+medicName.toString())

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMascota.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMascota().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}