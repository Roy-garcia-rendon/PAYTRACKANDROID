package com.example.paytrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paytrack.adapters.WalletAdapter
import com.example.paytrack.data.AppDatabase
import com.example.paytrack.data.entities.Wallet
import com.example.paytrack.data.repository.WalletRepository
import com.example.paytrack.databinding.ActivityWalletBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date

class WalletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWalletBinding
    private lateinit var walletAdapter: WalletAdapter
    private lateinit var walletRepository: WalletRepository
    private var currentUserId: Long = 1 // TODO: Obtener el ID del usuario actual de la sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupAddWalletButton()
        loadWalletData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        walletAdapter = WalletAdapter()
        binding.walletRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@WalletActivity)
            adapter = walletAdapter
        }
    }

    private fun setupAddWalletButton() {
        binding.fabAddWallet.setOnClickListener {
            createInitialWallet()
        }
    }

    private fun loadWalletData() {
        val database = AppDatabase.getDatabase(this)
        walletRepository = WalletRepository(database.walletDao())

        lifecycleScope.launch {
            try {
                walletRepository.getWalletsByUserId(currentUserId).collect { wallets ->
                    if (wallets.isEmpty()) {
                        binding.emptyStateLayout.visibility = android.view.View.VISIBLE
                        binding.walletRecyclerView.visibility = android.view.View.GONE
                    } else {
                        binding.emptyStateLayout.visibility = android.view.View.GONE
                        binding.walletRecyclerView.visibility = android.view.View.VISIBLE
                        walletAdapter.submitList(wallets)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@WalletActivity, "Error al cargar las billeteras: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createInitialWallet() {
        lifecycleScope.launch {
            try {
                val newWallet = Wallet(
                    userId = currentUserId,
                    balance = 0.0,
                    currency = "MXN",
                    createdAt = Date(),
                    updatedAt = Date()
                )
                walletRepository.insertWallet(newWallet)
                loadWalletData() // Recargar los datos
                Toast.makeText(this@WalletActivity, "Billetera creada exitosamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@WalletActivity, "Error al crear la billetera: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
} 