package com.example.paytrack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paytrack.data.entities.Wallet
import com.example.paytrack.databinding.ItemWalletBinding
import java.text.SimpleDateFormat
import java.util.*

class WalletAdapter : RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {
    private var wallets: List<Wallet> = emptyList()

    fun submitList(newWallets: List<Wallet>) {
        wallets = newWallets
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding = ItemWalletBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(wallets[position])
    }

    override fun getItemCount() = wallets.size

    class WalletViewHolder(private val binding: ItemWalletBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wallet: Wallet) {
            binding.tvCurrency.text = wallet.currency
            binding.tvBalance.text = String.format("%.2f", wallet.balance)
            
            val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            binding.tvLastUpdated.text = "Última actualización: ${dateFormat.format(wallet.updatedAt)}"
        }
    }
} 