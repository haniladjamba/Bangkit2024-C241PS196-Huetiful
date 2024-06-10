package com.bangkit2024.huetiful.ui.fragments.fulldetail

import android.content.Intent
import android.os.Bundle
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
                color = "#C44F6B"
            ),
            DetailPalateModel(
                color = "#FF859F"
            ),
            DetailPalateModel(
                color = "#FFBCD5"
            ),
            DetailPalateModel(
                color = "#F6D0C5"
            ),
            DetailPalateModel(
                color = "#FFBCD5"
            )
        )
    }
}