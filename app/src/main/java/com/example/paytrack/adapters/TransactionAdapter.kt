package com.example.paytrack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paytrack.R
import com.example.paytrack.data.entities.Transaction
import com.example.paytrack.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        fun bind(transaction: Transaction) {
            binding.apply {
                transactionDescription.text = transaction.description
                transactionDate.text = dateFormat.format(transaction.transactionDate)
                
                // Formatear el monto con el símbolo de moneda
                val amountText = String.format("$%.2f", transaction.amount)
                
                // Establecer el color del monto según el tipo de transacción
                val amountColor = if (transaction.type == "INCOME") {
                    root.context.getColor(R.color.green_500)
                } else {
                    root.context.getColor(R.color.red_500)
                }
                
                transactionAmount.text = amountText
                transactionAmount.setTextColor(amountColor)
            }
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
} 