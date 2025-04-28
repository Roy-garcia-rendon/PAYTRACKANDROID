package com.example.paytrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.paytrack.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupTransferButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupTransferButton() {
        binding.transferButton.setOnClickListener {
            val amount = binding.amountInput.text.toString()
            val recipient = binding.recipientInput.text.toString()
            val description = binding.descriptionInput.text.toString()

            if (amount.isEmpty() || recipient.isEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor completa todos los campos requeridos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Aquí iría la lógica para procesar la transferencia
                Toast.makeText(
                    this,
                    "Transferencia realizada",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
} 