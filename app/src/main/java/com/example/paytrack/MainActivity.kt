package com.example.paytrack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paytrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupBottomNavigation()
        setupQuickActions()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Ya estamos en la pantalla de inicio
                    true
                }
                R.id.navigation_wallet -> {
                    // Navegar a la pantalla de billetera
                    true
                }
                R.id.navigation_activity -> {
                    // Navegar a la pantalla de actividad
                    true
                }
                R.id.navigation_profile -> {
                    // Navegar a la pantalla de perfil
                    true
                }
                else -> false
            }
        }
    }

    private fun setupQuickActions() {
        // Aquí se configurarían los listeners para los botones de acciones rápidas
        // Por ahora solo implementamos el botón de QR
        binding.root.findViewById<android.widget.LinearLayout>(R.id.qrButton).setOnClickListener {
            startActivity(Intent(this, QRActivity::class.java))
        }
    }
}