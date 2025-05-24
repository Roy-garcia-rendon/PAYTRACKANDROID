package com.example.paytrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.paytrack.data.AppDatabase
import com.example.paytrack.data.entities.User
import com.example.paytrack.data.repository.UserRepository
import com.example.paytrack.databinding.ActivityEditProfileBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userRepository: UserRepository
    private var currentUserId: Long = 1
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUserId = intent.getLongExtra("USER_ID", 1L)
        setupRepositories()
        setupToolbar()
        loadUserData()
        setupSaveButton()
    }

    private fun setupRepositories() {
        val database = AppDatabase.getDatabase(this)
        userRepository = UserRepository(database.userDao())
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            try {
                currentUser = withContext(Dispatchers.IO) {
                    userRepository.getUserById(currentUserId)
                }
                
                currentUser?.let { user ->
                    binding.nameEditText.setText(user.name)
                    binding.emailEditText.setText(user.email)
                } ?: run {
                    Toast.makeText(this@EditProfileActivity, "Error al cargar datos del usuario", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditProfileActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            if (validateInput()) {
                updateUserProfile()
            }
        }
    }

    private fun validateInput(): Boolean {
        val name = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val currentPassword = binding.currentPasswordEditText.text.toString().trim()
        val newPassword = binding.newPasswordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

        if (name.isEmpty()) {
            binding.nameLayout.error = "El nombre es requerido"
            return false
        }

        if (email.isEmpty()) {
            binding.emailLayout.error = "El correo electrónico es requerido"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = "Ingrese un correo electrónico válido"
            return false
        }

        // Si se está cambiando la contraseña
        if (newPassword.isNotEmpty() || confirmPassword.isNotEmpty()) {
            if (currentPassword.isEmpty()) {
                binding.currentPasswordLayout.error = "La contraseña actual es requerida"
                return false
            }

            if (currentUser?.password != currentPassword) {
                binding.currentPasswordLayout.error = "La contraseña actual es incorrecta"
                return false
            }

            if (newPassword.length < 6) {
                binding.newPasswordLayout.error = "La nueva contraseña debe tener al menos 6 caracteres"
                return false
            }

            if (newPassword != confirmPassword) {
                binding.confirmPasswordLayout.error = "Las contraseñas no coinciden"
                return false
            }
        }

        return true
    }

    private fun updateUserProfile() {
        lifecycleScope.launch {
            try {
                val name = binding.nameEditText.text.toString().trim()
                val email = binding.emailEditText.text.toString().trim()
                val newPassword = binding.newPasswordEditText.text.toString().trim()

                currentUser?.let { user ->
                    // Verificar si el email ya está en uso por otro usuario
                    if (email != user.email) {
                        val existingUser = withContext(Dispatchers.IO) {
                            userRepository.getUserByEmail(email)
                        }
                        if (existingUser != null) {
                            Toast.makeText(this@EditProfileActivity, "El correo electrónico ya está en uso", Toast.LENGTH_SHORT).show()
                            return@launch
                        }
                    }

                    // Actualizar usuario
                    val updatedUser = user.copy(
                        name = name,
                        email = email,
                        password = if (newPassword.isNotEmpty()) newPassword else user.password,
                        updatedAt = System.currentTimeMillis()
                    )

                    withContext(Dispatchers.IO) {
                        userRepository.updateUser(updatedUser)
                    }

                    Toast.makeText(this@EditProfileActivity, "Perfil actualizado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditProfileActivity, "Error al actualizar perfil: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
} 