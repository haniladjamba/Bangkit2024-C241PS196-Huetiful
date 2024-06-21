package com.bangkit2024.huetiful.ui.activity.result

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
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
import com.google.gson.Gson
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
        val data = setupDataColor()
        findColorName(data)
        showAnalyzeImage()
        savedUserPalate()
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
            animatedFavoriteIcon()
            saveFavoriteItems()
        }
        binding.btnBack.setOnClickListener {
            navigateToHome()
        }
    }

    private fun animatedFavoriteIcon() {
        binding.iFavoriteFulldetail.setImageResource(R.drawable.heart_animated)
        val copyButtonAnim = binding.iFavoriteFulldetail.drawable as AnimatedVectorDrawable
        copyButtonAnim.start()
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun savedUserPalate() {
        val intent = intent
        val bundle = intent.extras
        val stringList = mutableListOf<String>()

        if (bundle != null) {
            val colorArrayList = bundle.getStringArrayList("colorList")
            if (!colorArrayList.isNullOrEmpty()) {
                for (color in colorArrayList) {
                    try {
                        val hexColor = String.format("#%06X", 0xFFFFFF and Integer.parseInt(color.substring(1), 16))
                        stringList.add("\'${hexColor.substring(1)}\'")
                    } catch (e: NumberFormatException) {
                        stringList.add("'FFFFFF'")
                    }
                }
            }
        }

        val jsonString = Gson().toJson(stringList)
        val sharedPreferences = getSharedPreferences("user_palate_preference", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("current_palate", jsonString).apply()
    }

    private fun saveFavoriteItems() {
        val intent = intent
        val bundle = intent.extras
        val extractedSkinTone = bundle?.getString("extractedSkinTone")
        val extractedPalate = bundle?.getStringArrayList("colorList")

        if (extractedSkinTone != null && extractedPalate != null) {
            val validatedPalette = extractedPalate.map { color ->
                try {
                    val hexColor = String.format("#%06X", 0xFFFFFF and Integer.parseInt(color.substring(1), 16))
                    hexColor
                } catch (e: NumberFormatException) {
                    "#FFFFFF"
                }
            }

            resultViewModel.saveFavoritePalate(extractedSkinTone, validatedPalette)
        }
        lifecycleScope.launch {
            resultViewModel.saveFavoriteState.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        makeToast(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        if (!result.shown) {
                            showPredictSavePalateError(result.error)
                            result.shown = true
                        }
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

    private fun setupDataColor(): List<String> {
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            val colorArrayList = bundle.getStringArrayList("colorList")
            if (!colorArrayList.isNullOrEmpty()) {
                val validatedPalette = colorArrayList.map { color ->
                    try {
                        val hexColor = String.format(
                            "#%06X",
                            0xFFFFFF and Integer.parseInt(color.substring(1), 16)
                        )
                        hexColor
                    } catch (e: NumberFormatException) {
                        "#FFFFFF"
                    }
                }

                return validatedPalette
            } else {
                return emptyList()
            }
        } else {
            return emptyList()
        }
    }

    private fun findColorName(colorData : List<String>) {

        val testList = mutableListOf<DetailPalateModel>()

        Log.d("colorData find", "$colorData")
        colorData.map { hex ->
            Log.d("Map", "$hex")
            resultViewModel.getColorName(hex)
        }

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

        resultViewModel.colorName.observe(this) { color ->
            val testList3 = DetailPalateModel(
                color = color.closestNamedHex,
                name = color.value
            )
            testList.add(testList3)

            setupAdapter(testList)
        }
    }

}