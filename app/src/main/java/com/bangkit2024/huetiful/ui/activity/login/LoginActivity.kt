package com.bangkit2024.huetiful.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.databinding.ActivityLoginBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.AuthViewModelFactory
import com.bangkit2024.huetiful.ui.activity.main.MainActivity
import com.bangkit2024.huetiful.ui.activity.resetpassword.ResetPasswordActivity
import com.bangkit2024.huetiful.ui.activity.signup.SignUpActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            userLogin()
        }
        binding.tvToSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.btnResetPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun userLogin() {
        val email = binding.edEmailLogin.text.toString()
        val password = binding.edPasswordLogin.text.toString()

        val loginValidity = checkLoginInfo(email, password)
        if (!loginValidity) {
            showToast("Try again")
        } else {
            lifecycleScope.launch {
                loginViewModel.login(email, password)
                loginViewModel.loginState.collect { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            navigateToHome()
                        }
                        is Result.Error -> {
                            showLoading(false)
                            if (!result.shown) {
                                showLoginError(result.error)
                                result.shown = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkLoginInfo(email: String, password: String) : Boolean {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Incorrect email format")
            return false
        }

        if (password.length < 8) {
            showToast("Password must be more than 8 character")
            return false
        }

        return true
    }

    private fun showLoginError(error: String) {
        Log.d(TAG, "Login failed with error: $error")
        showToast(error)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or  Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLogin.isVisible = isLoading
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}