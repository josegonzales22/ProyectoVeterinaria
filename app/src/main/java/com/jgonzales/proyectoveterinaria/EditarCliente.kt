package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.jgonzales.proyectoveterinaria.entidades.Cliente
import com.jgonzales.proyectoveterinaria.modelo.ClienteDAO

class EditarCliente : AppCompatActivity() {

    lateinit var imgRetrocederCliEditar: ImageView
    lateinit var etNombres: EditText
    lateinit var etApellidos: EditText
    lateinit var etCorreo: EditText
    lateinit var etCelular: EditText
    lateinit var btnEditGuardarCliente:Button

    //-------RETROCEDER AL DASHBOARD----------
    lateinit var medicName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cliente)

        inicializar()

        // Obtener datos del Intent
        val dniCliente = intent.getIntExtra("dniCliente", 0)
        val nombreCliente = intent.getStringExtra("nombreCliente")
        val apellidoCliente = intent.getStringExtra("apellidoCliente")
        val correoCliente = intent.getStringExtra("correoCliente")
        val celularCliente = intent.getIntExtra("celularCliente", 0)

        // Llenar el formulario con los datos del cliente
        etNombres.setText(nombreCliente)
        etApellidos.setText(apellidoCliente)
        etCorreo.setText(correoCliente)
        etCelular.setText(celularCliente.toString())

        //-------RETROCEDER AL DASHBOARD----------
        val bundle = intent.extras
        medicName = bundle?.getString("medicName").toString()


        // Agregar evento de clic al botón guardar
        btnEditGuardarCliente.setOnClickListener {
            // Obtener los valores actualizados de los campos
            val nuevoNombre = etNombres.text.toString().trim()
            val nuevoApellido = etApellidos.text.toString().trim()
            val nuevoCorreo = etCorreo.text.toString().trim()
            val nuevoCelular = etCelular.text.toString().trim()

            // Verificar que no haya campos vacíos
            if (nuevoNombre.isNotEmpty() && nuevoApellido.isNotEmpty() && nuevoCorreo.isNotEmpty() && nuevoCelular.isNotEmpty()) {
                // Actualizar la información en la base de datos
                val clienteDAO = ClienteDAO(this@EditarCliente)
                val clienteActualizado = Cliente().apply {
                    this.dniCliente = dniCliente
                    this.nombreCliente = nuevoNombre
                    this.apellidoCliente = nuevoApellido
                    this.correoCliente = nuevoCorreo
                    this.celularCliente = nuevoCelular.toInt()
                }

                val respuesta = clienteDAO.actualizarCliente(clienteActualizado)


                // Mostrar el resultado de la actualización
                Toast.makeText(this@EditarCliente, respuesta, Toast.LENGTH_SHORT).show()

                //-------RETROCEDER AL DASHBOARD----------
                val intent = Intent(this, ContenedorActivity::class.java)
                var bundle:Bundle = Bundle()
                bundle.putString("medicName", medicName)
                intent.putExtras(bundle)
                startActivity(intent)

            } else {
                // Mostrar mensaje si hay campos vacíos
                Toast.makeText(this@EditarCliente, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun inicializar(){
        btnEditGuardarCliente = findViewById(R.id.btnEditGuardarCliente)

        imgRetrocederCliEditar = findViewById(R.id.imgRetrocederCliEditar)
        imgRetrocederCliEditar.setOnClickListener{
            finish()
        }

        etNombres = findViewById(R.id.editNombreCliente)
        etApellidos = findViewById(R.id.editApellidoCliente)
        etCorreo = findViewById(R.id.editCorreoCliente)
        etCelular = findViewById(R.id.editCelularCliente)
    }



}