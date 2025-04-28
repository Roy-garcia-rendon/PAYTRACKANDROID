package com.example.paytrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paytrack.databinding.ActivityPayBinding

class PayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
} 