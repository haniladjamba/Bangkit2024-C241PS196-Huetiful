package com.bangkit2024.huetiful.ui.activity.resultpair

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.databinding.ActivityResultPairBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.FavoriteViewModelFactory
import com.bangkit2024.huetiful.ui.activity.main.MainActivity
import com.bangkit2024.huetiful.ui.activity.result.ResultViewModel
import kotlinx.coroutines.launch

class ResultPairActivity : AppCompatActivity() {

    private val resultViewModel by viewModels<ResultViewModel> {
        FavoriteViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityResultPairBinding
    private var isExpanded: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityResultPairBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
        showAnalyzedResult()
    }

    private fun showAnalyzedResult() {
        val intent = intent
        val image = intent.getStringExtra("itemImage")?.let { Uri.parse(it) }
        image.let {
            binding.ivPreviewImagePair.setImageURI(it)
        }

        val dominantColor = intent.getStringExtra("dominantColor")
        val predictedColor = intent.getStringExtra("predictedColor")

        if (dominantColor != null) {
            getColorName(dominantColor)
        }

        binding.tvColorInfoMain.text = dominantColor
        binding.tvColorInfoMain.setTextColor(Color.parseColor(dominantColor))
        binding.cvColor1.setCardBackgroundColor(Color.parseColor(dominantColor))
        binding.cvColor2.setCardBackgroundColor(Color.parseColor(predictedColor))
        binding.tvColor1.text = dominantColor
        binding.tvColor2.text = predictedColor
        binding.tvColor1.setTextColor(Color.parseColor(dominantColor))
        binding.tvColor2.setTextColor(Color.parseColor(predictedColor))
    }

    private fun getColorName(hex: String) {
        resultViewModel.getColorName(hex)

        lifecycleScope.launch {
            resultViewModel.getColorNameState.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                    }
                    is Result.Error -> {
                        showLoading(false)
                    }
                }
            }
        }

        resultViewModel.colorName.observe(this) {
            if (it != null) {
                binding.tvColorNameMain.text = it.value
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbResultpair.isVisible = isLoading
    }

    private fun setupAction() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun expand(view: View) {
        var counter = binding.cvColor1.translationY
        if (!isExpanded) {
            counter += 200f
            binding.vColor1.animate()
                .alpha(0f)
                .setDuration(300)
                .start()
            binding.tvColor1.animate()
                .alpha(0f)
                .setDuration(300)
                .start()
            view.animate()
                .translationY(counter)
                .setDuration(300)
                .start()
        } else {
            counter -= 200f
            binding.vColor1.animate()
                .alpha(1f)
                .setDuration(300)
                .start()
            binding.tvColor1.animate()
                .alpha(1f)
                .setDuration(300)
                .start()
            view.animate()
                .translationY(counter)
                .setDuration(300)
                .start()
        }
        isExpanded = !isExpanded
    }

    fun expand2(view: View) {
        var counter = binding.cvColor2.translationY
        if (!isExpanded) {
            counter += 200f
            binding.vColor2.animate()
                .alpha(0f)
                .setDuration(300)
                .start()
            binding.tvColor2.animate()
                .alpha(0f)
                .setDuration(300)
                .start()
            view.animate()
                .translationY(counter)
                .setDuration(300)
                .start()
        } else {
            counter -= 200f
            binding.vColor2.animate()
                .alpha(1f)
                .setDuration(300)
                .start()
            binding.tvColor2.animate()
                .alpha(1f)
                .setDuration(300)
                .start()
            view.animate()
                .translationY(counter)
                .setDuration(300)
                .start()
        }
        isExpanded = !isExpanded
    }
}