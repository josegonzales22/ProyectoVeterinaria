package com.jgonzales.proyectoveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContenedorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor)
        reemplazarFragmento(FragmentInicio())
        var navigationView:BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navInicio -> reemplazarFragmento(FragmentInicio())
                R.id.navCliente -> reemplazarFragmento(FragmentCliente())
                R.id.navMascota -> reemplazarFragmento(FragmentMascota())
                R.id.navMedicina -> reemplazarFragmento(FragmentMedicina())
                R.id.navMedico -> reemplazarFragmento(FragmentMedico())
            }
            true
        }

    }
    private fun reemplazarFragmento(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frLayout, fragment)
        fragmentTransaction.commit()
    }
}