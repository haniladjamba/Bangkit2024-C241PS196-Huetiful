package com.bangkit2024.huetiful.ui.activity.verification

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.databinding.ActivityVerificationBinding
import com.bangkit2024.huetiful.ui.activity.login.LoginActivity
import com.bangkit2024.huetiful.ui.activity.signup.SignUpActivity

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAnimation()
        setupAction()
    }

    private fun setupAnimation() {
        val animButtonStart = binding.main.background as AnimationDrawable
        animButtonStart.setEnterFadeDuration(1000)
        animButtonStart.setExitFadeDuration(2000)
        animButtonStart.start()
    }

    private fun setupAction() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}