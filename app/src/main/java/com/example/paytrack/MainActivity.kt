package com.example.paytrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paytrack.adapters.TransactionAdapter
import com.example.paytrack.data.AppDatabase
import com.example.paytrack.data.repository.TransactionRepository
import com.example.paytrack.data.repository.WalletRepository
import com.example.paytrack.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var walletRepository: WalletRepository
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var transactionAdapter: TransactionAdapter
    private var currentUserId: Long = 1 // TODO: Obtener el ID del usuario actual de la sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRepositories()
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        setupBottomNavigation()
        loadData()
    }

    private fun setupRepositories() {
        val database = AppDatabase.getDatabase(this)
        walletRepository = WalletRepository(database.walletDao())
        transactionRepository = TransactionRepository(database.transactionDao())
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter()
        binding.recentTransactionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = transactionAdapter
        }
    }

    private fun setupClickListeners() {
        binding.availableBalanceButton.setOnClickListener {
            startActivity(Intent(this, WalletActivity::class.java))
        }

        binding.payButton.setOnClickListener {
            startActivity(Intent(this, PayActivity::class.java))
        }

        binding.transferButton.setOnClickListener {
            startActivity(Intent(this, TransferActivity::class.java))
        }

        binding.qrButton.setOnClickListener {
            // TODO: Implementar funcionalidad QR
        }

        binding.moreButton.setOnClickListener {
            // TODO: Implementar menú de más opciones
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Ya estamos en la pantalla de inicio
                    true
                }
                R.id.navigation_wallet -> {
                    startActivity(Intent(this, WalletActivity::class.java))
                    true
                }
                R.id.navigation_activity -> {
                    startActivity(Intent(this, ActivityActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                // Cargar saldo total
                val wallets = walletRepository.getWalletsByUserId(currentUserId).first()
                val totalBalance = wallets.sumOf { it.balance }
                binding.totalBalanceText.text = String.format("$%.2f", totalBalance)

                // Cargar transacciones recientes
                val transactions = transactionRepository.getRecentTransactions().first()
                if (transactions.isEmpty()) {
                    binding.emptyTransactionsText.visibility = View.VISIBLE
                    binding.recentTransactionsRecyclerView.visibility = View.GONE
                } else {
                    binding.emptyTransactionsText.visibility = View.GONE
                    binding.recentTransactionsRecyclerView.visibility = View.VISIBLE
                    transactionAdapter.submitList(transactions)
                }
            } catch (e: Exception) {
                // Manejar errores
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData() // Recargar datos cuando la actividad se reanuda
    }
}