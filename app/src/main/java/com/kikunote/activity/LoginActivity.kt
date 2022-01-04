package com.kikunote.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kikunote.databinding.LoginActivityBinding
import com.kikunote.R
import com.kikunote.entity.User
import com.kikunote.session.UserSession
import com.kikunote.viewModel.UserViewModel

class LoginActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: LoginActivityBinding

    private lateinit var viewModel: UserViewModel
    private lateinit var session: UserSession


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initViewModel()
        initSession()
    }



    private fun initListener() {
        binding.registerText.setOnClickListener(this)
        binding.logInbutton.setOnClickListener(this)
    }

    private fun initSession() {
        session = UserSession(applicationContext)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onClick(v: View){
        when(v.id) {
            R.id.registerText -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.logInbutton -> {
                val email = binding.loginEmailInput.text.toString()
                val password = binding.loginPasswordInput.text.toString()

                if(email.isEmpty() || password.isEmpty()) {
                    return Toast
                        .makeText(this@LoginActivity, "Fields can't be empty", Toast.LENGTH_SHORT)
                        .show()
                }

                val successLogIn: User? =
                    viewModel.checkCredentials(email, password)

                if(successLogIn != null){
                    Toast
                        .makeText(this@LoginActivity, "Welcome", Toast.LENGTH_SHORT)
                        .show()

                    session.createLoginSession(successLogIn.name, successLogIn.email)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()

                } else {
                    return Toast
                        .makeText(this@LoginActivity, "Wrong credentials", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }
}