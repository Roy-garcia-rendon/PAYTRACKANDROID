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
        // Configurar el botón de pagar
        binding.payButton.setOnClickListener {
            startActivity(Intent(this, PayActivity::class.java))
        }

        // Configurar el botón de transferir
        binding.transferButton.setOnClickListener {
            startActivity(Intent(this, TransferActivity::class.java))
        }

        // Configurar el botón de QR
        binding.qrButton.setOnClickListener {
            startActivity(Intent(this, QRActivity::class.java))
        }

        // Configurar el botón de más
        binding.moreButton.setOnClickListener {
            // Aquí iría la lógica para mostrar más opciones
            android.widget.Toast.makeText(
                this,
                "Más opciones",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
}