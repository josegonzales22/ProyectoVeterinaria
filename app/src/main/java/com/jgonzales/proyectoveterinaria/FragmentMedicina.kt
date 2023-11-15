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
 * Use the [FragmentMedicina.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMedicina : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var txtMensaje:TextView

    lateinit var btnNuevaMedicina: Button
    lateinit var btnEditarMedicina: Button
    lateinit var btnEliminarMedicina: Button

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
        val view =  inflater.inflate(R.layout.fragment_medicina, container, false)

        btnNuevaMedicina = view.findViewById(R.id.btnNuevoMedicamento)
        btnNuevaMedicina.setOnClickListener{
            val intent = Intent(requireActivity(), NuevaMedicina::class.java)
            startActivity(intent)
        }

        btnEditarMedicina = view.findViewById(R.id.btnEditarMedicamento)
        btnEditarMedicina.setOnClickListener {
            val intent = Intent(requireActivity(), EditarMedicina::class.java)
            startActivity(intent)
        }

        btnEliminarMedicina = view.findViewById(R.id.btnEliminarMedicamento)
        btnEliminarMedicina.setOnClickListener {
            val intent = Intent(requireActivity(), EliminarMedicina::class.java)
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
         * @return A new instance of fragment FragmentMedicina.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMedicina().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}