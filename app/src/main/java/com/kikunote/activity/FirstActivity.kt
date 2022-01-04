package com.kikunote.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kikunote.R
import com.kikunote.databinding.FirstActivityBinding
import com.kikunote.session.UserSession

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: FirstActivityBinding

    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FirstActivityBinding.inflate(layoutInflater)
        this.getWindow()
            .setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        setContentView(binding.root)

        val session = UserSession(applicationContext)
        title = binding.titleSplashScreen
        image = binding.imageSplashScreen

        title.setAnimation(bottomAnimation)
        image.setAnimation(topAnimation)

        handler = Handler()
        handler.postDelayed( {
            if(!session.isLoggedIn()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish()
            }
        }, 2500)
    }
}