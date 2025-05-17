package com.example.paytrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.paytrack.data.AppDatabase
import com.example.paytrack.data.entities.Transaction
import com.example.paytrack.data.entities.Wallet
import com.example.paytrack.data.repository.TransactionRepository
import com.example.paytrack.data.repository.WalletRepository
import com.example.paytrack.databinding.ActivityTransferBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
    private lateinit var walletRepository: WalletRepository
    private lateinit var transactionRepository: TransactionRepository
    private var currentUserId: Long = 1 // TODO: Obtener el ID del usuario actual de la sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRepositories()
        setupToolbar()
        setupTransferButton()
    }

    private fun setupRepositories() {
        val database = AppDatabase.getDatabase(this)
        walletRepository = WalletRepository(database.walletDao())
        transactionRepository = TransactionRepository(database.transactionDao())
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
            val amountStr = binding.amountInput.text.toString()
            val recipient = binding.recipientInput.text.toString()
            val description = binding.descriptionInput.text.toString()

            if (amountStr.isEmpty() || recipient.isEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor completa todos los campos requeridos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val amount = amountStr.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(
                    this,
                    "Por favor ingresa un monto válido",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    // Obtener la billetera del usuario
                    val wallets = walletRepository.getWalletsByUserId(currentUserId).first()
                    if (wallets.isEmpty()) {
                        Toast.makeText(
                            this@TransferActivity,
                            "No tienes una billetera activa",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    val wallet = wallets.first()
                    if (wallet.balance < amount) {
                        Toast.makeText(
                            this@TransferActivity,
                            "Saldo insuficiente",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    // Actualizar el saldo de la billetera
                    val updatedWallet = wallet.copy(
                        balance = wallet.balance - amount,
                        updatedAt = Date()
                    )
                    walletRepository.updateWallet(updatedWallet)

                    // Crear la transacción
                    val transaction = Transaction(
                        userId = currentUserId,
                        walletId = wallet.id,
                        amount = amount,
                        type = "EXPENSE",
                        description = description,
                        categoryId = null,
                        transactionDate = Date(),
                        status = "COMPLETED",
                        createdAt = Date()
                    )
                    transactionRepository.insertTransaction(transaction)

                    Toast.makeText(
                        this@TransferActivity,
                        "Transferencia realizada exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(
                        this@TransferActivity,
                        "Error al realizar la transferencia: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
} 