package com.kikunote.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kikunote.databinding.RegisterActivityBinding

class RegisterActivity: AppCompatActivity() {
    private lateinit var binding: RegisterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}