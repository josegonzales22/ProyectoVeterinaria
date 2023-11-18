package com.jgonzales.proyectoveterinaria.dialog

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.jgonzales.proyectoveterinaria.databinding.DialogClienteBinding

class DialogCliente(
    private val onSubmitClickListener: (String) -> Unit
) :DialogFragment(){
    private lateinit var binding : DialogClienteBinding
    lateinit var dialog : AlertDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogClienteBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER_VERTICAL)
        binding.btnAceptar.setOnClickListener {
            onSubmitClickListener.invoke(binding.txtDniCliente.text.toString())
            dialog.dismiss()
        }
        binding.btnCancelar.setOnClickListener {
            dialog.dismiss()
        }
        return dialog
    }
    fun cerrarDialog(){
        dialog.dismiss()
    }
}