package com.jgonzales.proyectoveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.jgonzales.proyectoveterinaria.entidades.Mascota
import com.jgonzales.proyectoveterinaria.modelo.MascotaDAO

class EditarMascota : AppCompatActivity() {

    lateinit var imgRetrocederMasEditar: ImageView
    lateinit var etNombres: EditText
    lateinit var etEspecies: EditText
    lateinit var etRaza: EditText
    lateinit var etGenero: Spinner
    lateinit var btnEditGuardarMascota: Button

    //-------RETROCEDER AL DASHBOARD----------
    lateinit var medicName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)

        inicializar()

        // Obtener datos del Intent
        val idMascota = intent.getIntExtra("idMascota", 0)
        val nombreMascota = intent.getStringExtra("nombreMascota")
        val especieMascota = intent.getStringExtra("especieMascota")
        val razaMascota = intent.getStringExtra("razaMascota")
        val generoMascota = intent.getStringExtra("generoMascota")

        // Llenar el formulario con los datos del cliente
        etNombres.setText(nombreMascota)
        etEspecies.setText(especieMascota)
        etRaza.setText(razaMascota)

        //-------RETROCEDER AL DASHBOARD----------
        val bundle = intent.extras
        medicName = bundle?.getString("medicName").toString()


        // Obtener el array de opciones desde los recursos
        val opcionesGenero = resources.getStringArray(R.array.opciones)

        // Crear un ArrayAdapter usando el array de opciones y un diseño predeterminado para los elementos del spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesGenero)

        // Especificar el diseño a usar cuando aparece la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplicar el adaptador al spinner
        etGenero.adapter = adapter

        // Obtener el índice del género actual en las opciones
        val indiceGenero = opcionesGenero.indexOf(generoMascota)

        // Establecer la selección en el Spinner
        etGenero.setSelection(indiceGenero)



        // Agregar evento de clic al botón guardar
        btnEditGuardarMascota.setOnClickListener {
            // Obtener los valores actualizados de los campos
            val nuevoNombre = etNombres.text.toString().trim()
            val nuevoEspecie = etEspecies.text.toString().trim()
            val nuevoRaza = etRaza.text.toString().trim()
            val nuevoGenero = etGenero.selectedItem.toString().trim()


            // Verificar que no haya campos vacíos
            if (nuevoNombre.isNotEmpty() && nuevoEspecie.isNotEmpty() && nuevoRaza.isNotEmpty() && nuevoGenero.isNotEmpty()) {
                // Actualizar la información en la base de datos
                val mascotaDAO = MascotaDAO(this@EditarMascota)
                val mascotaActualizado = Mascota().apply {
                    this.idMascota = idMascota
                    this.nombreMascota = nuevoNombre
                    this.especieMascota = nuevoEspecie
                    this.razaMascota = nuevoRaza
                    this.generoMascota = nuevoGenero
                }

                val respuesta = mascotaDAO.actualizarMascota(mascotaActualizado)


                // Mostrar el resultado de la actualización
                Toast.makeText(this@EditarMascota, respuesta, Toast.LENGTH_SHORT).show()

                //-------RETROCEDER AL DASHBOARD----------
                val intent = Intent(this, ContenedorActivity::class.java)
                var bundle:Bundle = Bundle()
                bundle.putString("medicName", medicName)
                intent.putExtras(bundle)
                startActivity(intent)

            } else {
                // Mostrar mensaje si hay campos vacíos
                Toast.makeText(this@EditarMascota, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun inicializar(){
        btnEditGuardarMascota = findViewById(R.id.btnEditGuardarMascota)

        imgRetrocederMasEditar = findViewById(R.id.imgRetrocederMasEditar)
        imgRetrocederMasEditar.setOnClickListener{
            finish()
        }

        etNombres = findViewById(R.id.editNombreMascota)
        etEspecies = findViewById(R.id.editEspecieMascota)
        etRaza = findViewById(R.id.editRazaMascota)
        etGenero = findViewById(R.id.spinnerGeneroMascota)
    }

}