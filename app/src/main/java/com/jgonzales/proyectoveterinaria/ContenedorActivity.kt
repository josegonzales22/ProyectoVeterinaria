package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContenedorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor)

        val bundle = intent.extras
        val medicName = bundle?.getString("medicName")
        val args:Bundle = Bundle()
        args.putString("medicName", medicName)
        reemplazarFragmento(FragmentInicio(), args)

        var navigationView:BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navInicio -> reemplazarFragmento(FragmentInicio(), args)
                R.id.navCliente -> reemplazarFragmento(FragmentCliente(), args)
                R.id.navMascota -> reemplazarFragmento(FragmentMascota(), args)
                R.id.navMedicina -> reemplazarFragmento(FragmentMedicina(), args)
                R.id.navMedico -> reemplazarFragmento(FragmentMedico(), args)
            }
            true
        }
    }
    private fun reemplazarFragmento(fragment:Fragment, args:Bundle){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frLayout, fragment)
        fragment.arguments=args
        fragmentTransaction.commit()
    }
}