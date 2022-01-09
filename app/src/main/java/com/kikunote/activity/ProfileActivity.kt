package com.kikunote.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kikunote.R
import com.kikunote.databinding.ActivityMainBinding
import com.kikunote.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)

    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }
}