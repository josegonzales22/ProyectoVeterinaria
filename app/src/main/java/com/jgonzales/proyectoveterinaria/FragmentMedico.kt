package com.jgonzales.proyectoveterinaria

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMedico.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMedico : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var btnNuevoMedico: Button
    lateinit var btnEditarMedico: Button
    lateinit var btnEliminarMedico: Button

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

        btnNuevoMedico = view.findViewById(R.id.btnNuevoMedico)
        btnNuevoMedico.setOnClickListener{
            val intent = Intent(requireActivity(), NuevoMedico::class.java)
            startActivity(intent)
        }

        btnEditarMedico = view.findViewById(R.id.btnEditarMedico)
        btnEditarMedico.setOnClickListener {
            val intent = Intent(requireActivity(), EditarMedico::class.java)
            startActivity(intent)
        }

        btnEliminarMedico = view.findViewById(R.id.btnEliminarMedico)
        btnEliminarMedico.setOnClickListener {
            val intent = Intent(requireActivity(), EliminarMedico::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMedico.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMedico().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}