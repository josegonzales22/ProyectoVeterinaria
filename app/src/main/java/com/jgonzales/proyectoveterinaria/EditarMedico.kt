package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.jgonzales.proyectoveterinaria.entidades.Medico
import com.jgonzales.proyectoveterinaria.modelo.MedicoDAO

class EditarMedico : AppCompatActivity() {

    lateinit var imgRetrocederMediEditar: ImageView
    lateinit var etNombres: EditText
    lateinit var etApellidos: EditText
    lateinit var etCorreo: EditText
    lateinit var etCelular: EditText
    lateinit var btnEditGuardarMedico: Button

    //-------RETROCEDER AL DASHBOARD----------
    lateinit var medicName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_medico)

        inicializar()

        // Obtener datos del Intent
        val dniMedico = intent.getStringExtra("dniMedico")
        val nombreMedico = intent.getStringExtra("nombreMedico")
        val apellidoMedico = intent.getStringExtra("apellidoMedico")
        val correoMedico = intent.getStringExtra("correoMedico")
        val celularMedico = intent.getStringExtra("celularMedico")

        // Llenar el formulario con los datos del cliente
        etNombres.setText(nombreMedico)
        etApellidos.setText(apellidoMedico)
        etCorreo.setText(correoMedico)
        etCelular.setText(celularMedico.toString())

        //-------RETROCEDER AL DASHBOARD----------
        val bundle = intent.extras
        medicName = bundle?.getString("medicName").toString()

        // Agregar evento de clic al botón guardar
        btnEditGuardarMedico.setOnClickListener {
            // Obtener los valores actualizados de los campos
            val nuevoNombre = etNombres.text.toString().trim()
            val nuevoApellido = etApellidos.text.toString().trim()
            val nuevoCorreo = etCorreo.text.toString().trim()
            val nuevoCelular = etCelular.text.toString().trim()

            // Verificar que no haya campos vacíos
            if (nuevoNombre.isNotEmpty() && nuevoApellido.isNotEmpty() && nuevoCorreo.isNotEmpty() && nuevoCelular.isNotEmpty()) {
                // Actualizar la información en la base de datos
                val medicoDAO = MedicoDAO(this@EditarMedico)
                val medicoActualizado = Medico().apply {
                    this.dniMedico = dniMedico.toString()
                    this.nombreMedico = nuevoNombre
                    this.apellidoMedico = nuevoApellido
                    this.correoMedico = nuevoCorreo
                    this.celularMedico = nuevoCelular
                }

                val respuesta = medicoDAO.actualizarMedico(medicoActualizado)


                // Mostrar el resultado de la actualización
                Toast.makeText(this@EditarMedico, respuesta, Toast.LENGTH_SHORT).show()

                //-------RETROCEDER AL DASHBOARD----------
                val intent = Intent(this, ContenedorActivity::class.java)
                var bundle:Bundle = Bundle()
                bundle.putString("medicName", medicName)
                intent.putExtras(bundle)
                startActivity(intent)

            } else {
                // Mostrar mensaje si hay campos vacíos
                Toast.makeText(this@EditarMedico, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun inicializar(){
        btnEditGuardarMedico = findViewById(R.id.btnEditGuardarMedico)

        imgRetrocederMediEditar = findViewById(R.id.imgRetrocederMediEditar)
        imgRetrocederMediEditar.setOnClickListener{
            finish()
        }

        etNombres = findViewById(R.id.editNombreMedico)
        etApellidos = findViewById(R.id.editApellidoMedico)
        etCorreo = findViewById(R.id.editCorreoMedico)
        etCelular = findViewById(R.id.editCelularMedico)
    }

}