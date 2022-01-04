package com.kikunote.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.kikunote.databinding.LoginActivityBinding
import com.kikunote.R

class LoginActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: LoginActivityBinding

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }



    private fun initListener() {
        binding.registerText.setOnClickListener(this)
    }

    override fun onClick(v: View){
        when(v.id) {
            R.id.registerText -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}