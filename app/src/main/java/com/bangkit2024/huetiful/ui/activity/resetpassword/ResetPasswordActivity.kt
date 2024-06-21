package com.bangkit2024.huetiful.ui.activity.resetpassword

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.databinding.ActivityResetPasswordBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.AuthViewModelFactory
import com.bangkit2024.huetiful.ui.activity.login.LoginActivity
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {

    private val resetPasswordViewModel by viewModels<ResetPasswordViewModel> {
        AuthViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
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
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.tvReset.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val email = binding.edEmailResetPassword.text.toString()

        lifecycleScope.launch {
            resetPasswordViewModel.resetPassword(email)
            resetPasswordViewModel.resetPasswordState.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        makeToast(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        makeToast(result.error)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbResetPassword.isVisible = isLoading
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}