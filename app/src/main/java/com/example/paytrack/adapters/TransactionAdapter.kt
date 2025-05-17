package com.example.paytrack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paytrack.R
import com.example.paytrack.data.entities.Transaction
import com.example.paytrack.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    private var transactions: List<Transaction> = emptyList()

    fun submitList(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount() = transactions.size

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            binding.tvTransactionTitle.text = transaction.description
            binding.tvTransactionAmount.text = String.format("%.2f", transaction.amount)
            
            // Set amount color based on transaction type
            val amountColor = if (transaction.type == "INCOME") {
                R.color.green_500
            } else {
                R.color.red_500
            }
            binding.tvTransactionAmount.setTextColor(
                binding.root.context.getColor(amountColor)
            )

            // Set icon based on transaction type
            val iconRes = when (transaction.type) {
                "INCOME" -> R.drawable.ic_payment
                "EXPENSE" -> R.drawable.ic_payment
                else -> R.drawable.ic_payment
            }
            binding.ivTransactionIcon.setImageResource(iconRes)

            val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            binding.tvTransactionDate.text = dateFormat.format(transaction.transactionDate)
        }
    }
} 