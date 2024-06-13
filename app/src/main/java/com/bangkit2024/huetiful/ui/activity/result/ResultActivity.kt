package com.bangkit2024.huetiful.ui.activity.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.huetiful.data.local.model.DetailPalateModel
import com.bangkit2024.huetiful.databinding.ActivityResultBinding
import com.bangkit2024.huetiful.ui.activity.main.MainActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
        val dataColor = setupDataColor()
        setupAdapter(dataColor)
        showAnalizeImage()
    }

    private fun showAnalizeImage() {
        val intent = intent
        val image = intent.getStringExtra("itemImage")?.let { Uri.parse(it) }
        image.let {
            binding.ivPreviewImageFulldetail.setImageURI(it) }
    }

    private fun setupAction() {
        binding.iFavoriteFulldetail.setOnClickListener {
            makeToast("Icon favorite clicked")
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
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