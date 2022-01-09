package com.kikunote.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kikunote.R
import com.kikunote.databinding.ActivityMainBinding
import com.kikunote.databinding.ActivityProfileBinding
import com.kikunote.entity.Note
import kotlinx.android.synthetic.main.activity_edit.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        initListener()
        setupToolbar()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
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
                val newPassword = binding.profilePassword.text.toString()
                val confirmNewPassword = binding.profileConfirmPassword.text.toString()

                changeName(newProfileName)
                changePassword(newPassword, confirmNewPassword)
            }
        }
    }

    private fun changeName(newName: String) {
        if (!newName.isEmpty()) {

        }
    }

    private fun changePassword(newPassword: String, confirmNewPassword: String) {
        if (!newPassword.isEmpty()) {
            if (newPassword == confirmNewPassword) {

            } else {
                return Toast
                    .makeText(this@ProfileActivity, "Passwords do not match", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}