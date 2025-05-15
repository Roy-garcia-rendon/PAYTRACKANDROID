package com.example.paytrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.paytrack.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (validateInput(name, email, password, confirmPassword)) {
                // Aquí iría la lógica de registro
                // Por ahora, solo mostramos un mensaje de éxito
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String, confirmPassword: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.nameLayout.error = "El nombre es requerido"
            isValid = false
        }

        if (email.isEmpty()) {
            binding.emailLayout.error = "El correo electrónico es requerido"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordLayout.error = "La contraseña es requerida"
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordLayout.error = "Confirma tu contraseña"
            isValid = false
        }

        if (password != confirmPassword) {
            binding.confirmPasswordLayout.error = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }
} 