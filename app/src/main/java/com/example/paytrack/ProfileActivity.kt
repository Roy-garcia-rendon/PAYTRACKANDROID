package com.example.paytrack

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.paytrack.data.AppDatabase
import com.example.paytrack.data.repository.UserRepository
import com.example.paytrack.databinding.ActivityProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userRepository: UserRepository
    private var currentUserId: Long = 1 // Valor por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ID del usuario del intent
        currentUserId = intent.getLongExtra("USER_ID", 1L)

        setupRepositories()
        setupToolbar()
        setupClickListeners()
        loadUserData()
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
                val user = withContext(Dispatchers.IO) {
                    userRepository.getUserById(currentUserId)
                }
                
                user?.let {
                    binding.userName.text = it.name
                    binding.userEmail.text = it.email
                } ?: run {
                    Toast.makeText(this@ProfileActivity, "Error al cargar datos del usuario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("USER_ID", currentUserId)
            startActivity(intent)
        }

        binding.securityButton.setOnClickListener {
            // TODO: Implementar la configuración de seguridad
            showToast("Seguridad")
        }

        binding.notificationsButton.setOnClickListener {
            // TODO: Implementar la configuración de notificaciones
            showToast("Notificaciones")
        }

        binding.helpButton.setOnClickListener {
            // TODO: Implementar la ayuda y soporte
            showToast("Ayuda y soporte")
        }

        binding.logoutButton.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                // Redirigir al login y limpiar el stack de actividades
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadUserData() // Recargar datos cuando la actividad se reanuda
    }
} 