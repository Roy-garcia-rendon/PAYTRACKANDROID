package com.example.paytrack

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.paytrack.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateInput(email, password)) {
                // Aquí iría la lógica de autenticación
                // Por ahora, solo verificamos que los campos no estén vacíos
                if (email == "usuario@ejemplo.com" && password == "123456") {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.registerPrompt.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.emailLayout.error = "El correo electrónico es requerido"
            return false
        }
        if (password.isEmpty()) {
            binding.passwordLayout.error = "La contraseña es requerida"
            return false
        }
        return true
    }
} 