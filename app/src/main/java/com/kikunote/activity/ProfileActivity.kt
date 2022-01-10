package com.kikunote.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kikunote.R
import com.kikunote.databinding.ActivityMainBinding
import com.kikunote.databinding.ActivityProfileBinding
import com.kikunote.entity.Note
import com.kikunote.entity.User
import com.kikunote.session.UserSession
import com.kikunote.viewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_edit.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var session: UserSession
    private lateinit var userEmail: String
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)

        initSession()
        initViewModel()
        initUser()
        initWelcomeText()
        initListener()
        setupToolbar()
    }



    private fun initViewModel() {
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private fun initSession() {
        session = UserSession(applicationContext)
        val userDetails = session.getUserDetails()
        userEmail = userDetails["email"].toString()

    }

    private fun initWelcomeText() {
        val userDetails = session.getUserDetails()
        val userName = userDetails["username"].toString()
        binding.welcomeText.text = userName

    }

    private fun initUser(){
        user = userViewModel.getCurrentUser(userEmail)
    }

    private fun setupToolbar() {
        binding.toolbar.toolbarTitle.text = "Edit profile"
    }

    private fun initListener() {
        binding.toolbar.nibBack.setOnClickListener(this)
        binding.toolbar.btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.nib_back -> {
                onBackPressed()
            }
            R.id.btn_save -> {
                val newProfileName = binding.profileName.text.toString()


                val confirmPassword = binding.profileConfirmPassword.text.toString()
                val newPassword = binding.profileNewPassword.text.toString()
                val confirmNewPassword = binding.profileConfirmPassword.text.toString()

                changeName(newProfileName)
                changePassword(confirmPassword, newPassword, confirmNewPassword)
            }
        }
    }

    private fun changeName(newName: String) {
        if (!newName.isEmpty()) {
            userViewModel.updateUser(
                User (
                    id = user.id,
                    name = newName,
                    email = user.email,
                    password = user.password
                        )
            )

            session.createLoginSession(newName, user.email)
            initWelcomeText()

            Toast
                .makeText(this@ProfileActivity, "Name changed", Toast.LENGTH_SHORT)
                .show()

            return onBackPressed()
        }
    }

    private fun changePassword(confirmPassword: String, newPassword: String, confirmNewPassword: String) {
        if (!newPassword.isEmpty()) {
            if(confirmPassword != user.password) {
                return Toast
                    .makeText(this@ProfileActivity, "Current password is wrong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (newPassword == confirmNewPassword) {
                    userViewModel.updateUser(User(
                        id = user.id,
                        name = user.name,
                        email = user.email,
                        password = newPassword
                    ))

                     Toast
                        .makeText(this@ProfileActivity, "Password was changed", Toast.LENGTH_SHORT)
                        .show()
                    return onBackPressed()
                } else {
                    return Toast
                        .makeText(this@ProfileActivity, "Passwords do not match", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }
}