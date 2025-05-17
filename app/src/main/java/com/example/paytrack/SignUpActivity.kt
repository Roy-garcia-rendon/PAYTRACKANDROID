package com.example.paytrack

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.paytrack.data.AppDatabase
import com.example.paytrack.data.entities.User
import com.example.paytrack.data.repository.UserRepository
import com.example.paytrack.databinding.ActivitySignupBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            // Initialize database and repository
            val database = AppDatabase.getDatabase(this)
            userRepository = UserRepository(database.userDao())

            setupRegisterButton()
            setupLoginButton()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al inicializar la aplicación: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRegisterButton() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (!validateInput(name, email, password, confirmPassword)) {
                return@setOnClickListener
            }

            binding.btnRegister.isEnabled = false // Deshabilitar el botón durante el proceso

            lifecycleScope.launch {
                try {
                    // Check if email already exists
                    val existingUser = withContext(Dispatchers.IO) {
                        userRepository.getUserByEmail(email)
                    }
                    
                    if (existingUser != null) {
                        Toast.makeText(this@SignUpActivity, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    // Create new user
                    val newUser = User(
                        name = name,
                        email = email,
                        password = password,
                        profileImage = null,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )

                    val userId = withContext(Dispatchers.IO) {
                        userRepository.insertUser(newUser)
                    }
                    
                    if (userId > 0) {
                        Toast.makeText(this@SignUpActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@SignUpActivity, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@SignUpActivity, "Error al registrar usuario: ${e.message}", Toast.LENGTH_LONG).show()
                } finally {
                    binding.btnRegister.isEnabled = true // Habilitar el botón nuevamente
                }
            }
        }
    }

    private fun setupLoginButton() {
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validateInput(name: String, email: String, password: String, confirmPassword: String): Boolean {
        if (name.isEmpty()) {
            binding.etName.error = "El nombre es requerido"
            return false
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "El correo electrónico es requerido"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Ingrese un correo electrónico válido"
            return false
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "La contraseña es requerida"
            return false
        }

        if (password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Confirme su contraseña"
            return false
        }

        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Las contraseñas no coinciden"
            return false
        }

        return true
    }
} 