package com.kikunote.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kikunote.R
import com.kikunote.databinding.RegisterActivityBinding
import com.kikunote.entity.User
import com.kikunote.session.UserSession
import com.kikunote.viewModel.UserViewModel

class RegisterActivity: AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: RegisterActivityBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var session: UserSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initListener()
        initViewModel()
        initSession()
    }

    private fun initListener() {
        binding.alreadyUser.setOnClickListener(this)
        binding.registerButton.setOnClickListener(this)
    }

    private fun initSession() {
        session = UserSession(applicationContext)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onClick(v: View){
        when(v.id) {
            R.id.alreadyUser -> {
                finish()
            }
            R.id.registerButton -> {
                val name = binding.registerNameInput.text.toString()
                val email = binding.registerEmailInput.text.toString()
                val password = binding.registerPasswordInput.text.toString()
                val passwordConfirm = binding.registerConfirmPasswordInput.text.toString()


                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()){
                    return Toast
                        .makeText(this@RegisterActivity, "Fields can't be empty", Toast.LENGTH_SHORT)
                        .show()

                }

                if(password != passwordConfirm){
                    return Toast
                        .makeText(this@RegisterActivity, "Passwords do not match", Toast.LENGTH_SHORT)
                        .show()
                }

                val successCreatingNewUser =
                    viewModel
                        .insertNewUser(
                            User(
                                name = name,
                                email = email,
                                password = password))

                if(successCreatingNewUser){
                    Toast
                        .makeText(this@RegisterActivity, "New user successfully created", Toast.LENGTH_SHORT)
                        .show()

                    session.createLoginSession(name, email)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()

                } else {
                    return Toast
                        .makeText(this@RegisterActivity, "This email is taken", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}