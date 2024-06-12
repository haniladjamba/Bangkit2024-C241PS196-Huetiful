package com.bangkit2024.huetiful.ui.fragments.fulldetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.data.local.model.DetailPalateModel
import com.bangkit2024.huetiful.databinding.FragmentFullDetailBinding
import com.bangkit2024.huetiful.ui.activity.main.MainActivity

class FullDetailFragment : Fragment() {

    private var _binding : FragmentFullDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        val dummyData = setupDummyData()
        setupAdapter(dummyData)
        showAnalizeImage()
    }

    private fun showAnalizeImage() {
        val intent = requireActivity().intent
        val image = intent.getStringExtra("itemImage")?.let { Uri.parse(it) }
        image.let {
            Log.d("FullDetailFragment", "image uri: $it")
            binding.ivPreviewImageFulldetail.setImageURI(it) }
    }

    private fun setupAction() {
        binding.iFavoriteFulldetail.setOnClickListener {
            makeToast("Icon favorite clicked")
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setupAdapter(data: List<DetailPalateModel>) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFullDetail.layoutManager = layoutManager

        val fullDetailAdapter = FullDetailAdapter()
        binding.rvFullDetail.adapter = fullDetailAdapter
        fullDetailAdapter.submitList(data)
    }

    private fun setupDummyData(): List<DetailPalateModel> {
        return listOf(
            DetailPalateModel(
                color = "#6e8eb4"
            ),
            DetailPalateModel(
                color = "#d3a157"
            ),
            DetailPalateModel(
                color = "#c57a39"
            ),
            DetailPalateModel(
                color = "#44a6ab"
            ),
            DetailPalateModel(
                color = "#c47b89"
            )
        )
    }
}