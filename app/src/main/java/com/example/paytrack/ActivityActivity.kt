package com.example.paytrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paytrack.adapters.TransactionAdapter
import com.example.paytrack.data.AppDatabase
import com.example.paytrack.data.repository.TransactionRepository
import com.example.paytrack.databinding.ActivityActivityBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ActivityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivityBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transactionRepository: TransactionRepository
    private var currentUserId: Long = 1 // TODO: Obtener el ID del usuario actual de la sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        loadTransactionData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter()
        binding.activityRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ActivityActivity)
            adapter = transactionAdapter
        }
    }

    private fun loadTransactionData() {
        val database = AppDatabase.getDatabase(this)
        transactionRepository = TransactionRepository(database.transactionDao())

        lifecycleScope.launch {
            try {
                transactionRepository.getTransactionsByUserId(currentUserId).collect { transactions ->
                    if (transactions.isEmpty()) {
                        binding.emptyStateLayout.visibility = android.view.View.VISIBLE
                        binding.activityRecyclerView.visibility = android.view.View.GONE
                    } else {
                        binding.emptyStateLayout.visibility = android.view.View.GONE
                        binding.activityRecyclerView.visibility = android.view.View.VISIBLE
                        transactionAdapter.submitList(transactions)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@ActivityActivity, "Error al cargar las transacciones: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
} 