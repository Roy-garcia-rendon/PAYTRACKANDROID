package com.example.paytrack

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paytrack.adapters.TransactionAdapter
import com.example.paytrack.data.AppDatabase
import com.example.paytrack.data.repository.TransactionRepository
import com.example.paytrack.data.repository.WalletRepository
import com.example.paytrack.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var walletRepository: WalletRepository
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var transactionAdapter: TransactionAdapter
    private var currentUserId: Long = 1 // Valor por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ID del usuario del intent
        currentUserId = intent.getLongExtra("USER_ID", 1L)

        setupRepositories()
        setupToolbar()
        setupRecyclerView()
        setupDrawer()
        setupClickListeners()
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

    private fun setupDrawer() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuProfile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("USER_ID", currentUserId)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(Gravity.END)
                    true
                }
                R.id.menuWallet -> {
                    val intent = Intent(this, WalletActivity::class.java)
                    intent.putExtra("USER_ID", currentUserId)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(Gravity.END)
                    true
                }
                R.id.menuActivity -> {
                    val intent = Intent(this, ActivityActivity::class.java)
                    intent.putExtra("USER_ID", currentUserId)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(Gravity.END)
                    true
                }
                R.id.menuSecurity -> {
                    val intent = Intent(this, SecurityActivity::class.java)
                    intent.putExtra("USER_ID", currentUserId)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(Gravity.END)
                    true
                }
                R.id.menuHelp -> {
                    val intent = Intent(this, SupportActivity::class.java)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(Gravity.END)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupClickListeners() {
        binding.availableBalanceButton.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            intent.putExtra("USER_ID", currentUserId)
            startActivity(intent)
        }

        binding.payButton.setOnClickListener {
            val intent = Intent(this, PayActivity::class.java)
            intent.putExtra("USER_ID", currentUserId)
            startActivity(intent)
        }

        binding.transferButton.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("USER_ID", currentUserId)
            startActivity(intent)
        }

        binding.qrButton.setOnClickListener {
            // TODO: Implementar funcionalidad QR
        }

        binding.moreButton.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.END)
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

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(Gravity.END)) {
            binding.drawerLayout.closeDrawer(Gravity.END)
        } else {
            super.onBackPressed()
        }
    }
}