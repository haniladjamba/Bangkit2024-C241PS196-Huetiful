package com.bangkit2024.huetiful.ui.activity.result

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.local.model.DetailPalateModel
import com.bangkit2024.huetiful.databinding.ActivityResultBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.FavoriteViewModelFactory
import com.bangkit2024.huetiful.ui.activity.main.MainActivity
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private val resultViewModel by viewModels<ResultViewModel> {
        FavoriteViewModelFactory.getInstance(this)
    }
    private lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
        val dataColor = setupDataColor()
        setupAdapter(dataColor)
        showAnalyzeImage()
    }

    private fun showAnalyzeImage() {
        val intent = intent
        val image = intent.getStringExtra("itemImage")?.let { Uri.parse(it) }
        if (image != null) {
            image.let {
                binding.ivPreviewImageFulldetail.setImageURI(it) }
        } else {
            val bundle = intent.extras
            val extractedSkinTone = bundle?.getString("extractedSkinTone")
            binding.ivPreviewImageFulldetail.setBackgroundColor(Color.parseColor(extractedSkinTone))
        }

    }

    private fun setupAction() {
        binding.iFavoriteFulldetail.setOnClickListener {
            makeToast("Item saved")
//            saveFavoriteItems()
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun saveFavoriteItems() {
        val intent = intent
        val bundle = intent.extras
        val extractedSkinTone = bundle?.getString("extractedSkinTone")
        val extractedPalate = bundle?.getStringArrayList("colorList")

        if (extractedSkinTone != null && extractedPalate != null) {
            Log.d("ResultActivity", "attempting to save : skinTone : $extractedSkinTone,  palate : $extractedPalate")
            resultViewModel.saveFavoritePalate(extractedSkinTone, extractedPalate)
        }
        lifecycleScope.launch {
            resultViewModel.saveFavoriteState.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        makeToast(getString(R.string.item_saved))
                    }
                    is Result.Error -> {
                        showPredictSavePalateError(result.error)
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showPredictSavePalateError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbResult.isVisible = isLoading
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupAdapter(data: List<DetailPalateModel>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvFullDetail.layoutManager = layoutManager

        val resultAdapter = ResultAdapter()
        binding.rvFullDetail.adapter = resultAdapter
        resultAdapter.submitList(data)
    }

    private fun setupDataColor(): List<DetailPalateModel> {
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            val colorArrayList = bundle.getStringArrayList("colorList")
            if (!colorArrayList.isNullOrEmpty()) {
                val colorList: List<DetailPalateModel> = colorArrayList.map { color ->
                    DetailPalateModel(color = color)
                }
                return colorList
            } else {
                return emptyList()
            }
        } else {
            return emptyList()
        }
    }
}