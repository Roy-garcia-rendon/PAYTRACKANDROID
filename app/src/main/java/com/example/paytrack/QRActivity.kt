package com.example.paytrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paytrack.databinding.ActivityQrBinding

class QRActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupShareButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupShareButton() {
        binding.shareButton.setOnClickListener {
            // Aquí iría la lógica para compartir el código QR
            // Por ahora solo mostramos un mensaje
            android.widget.Toast.makeText(
                this,
                "Función de compartir código QR",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
} 