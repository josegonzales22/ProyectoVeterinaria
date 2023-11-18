package com.jgonzales.proyectoveterinaria.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.jgonzales.proyectoveterinaria.databinding.DialogMedicinaBinding
import com.jgonzales.proyectoveterinaria.databinding.DialogMedicoBinding

class DialogMedico(
    private val onSubmitClickListener: (String) -> Unit
) : DialogFragment(){
    private lateinit var binding : DialogMedicoBinding
    lateinit var dialog : AlertDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogMedicoBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER_VERTICAL)
        binding.btnAceptar.setOnClickListener {
            onSubmitClickListener.invoke(binding.txtIdMedico.text.toString())
            dialog.dismiss()
        }
        binding.btnCancelar.setOnClickListener {
            dialog.dismiss()
        }
        return dialog
    }
}