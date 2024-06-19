package com.bangkit2024.huetiful.ui.activity.resultpair

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.huetiful.databinding.ActivityResultPairBinding
import com.bangkit2024.huetiful.ui.activity.main.MainActivity

class ResultPairActivity : AppCompatActivity() {

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

        binding.tvColorInfoMain.text = dominantColor
        binding.tvColorInfoMain.setTextColor(Color.parseColor(dominantColor))
        binding.cvColor1.setCardBackgroundColor(Color.parseColor(dominantColor))
        binding.cvColor2.setCardBackgroundColor(Color.parseColor(predictedColor))
        binding.tvColor1.text = dominantColor
        binding.tvColor2.text = predictedColor
        binding.tvColor1.setTextColor(Color.parseColor(dominantColor))
        binding.tvColor2.setTextColor(Color.parseColor(predictedColor))
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