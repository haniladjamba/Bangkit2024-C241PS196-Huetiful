package com.bangkit2024.huetiful.ui.fragments.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.remote.response.GetFavoriteDataResponseItem
import com.bangkit2024.huetiful.databinding.FragmentFavoriteBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.FavoriteViewModelFactory
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadPalateData()
    }

    private fun loadPalateData() {
        favoriteViewModel.getFavoriteData()

        lifecycleScope.launch {
            favoriteViewModel.getFavoriteDataState.collect { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> showLoading(false)
                    is Result.Error -> {
                        showGetFavoriteDataError(result.error)
                        showLoading(false)
                    }
                }
            }
        }
        favoriteViewModel.favoriteData.observe(viewLifecycleOwner) { favoriteData ->
            if (favoriteData != null) {
                Log.d("FavoriteFragment", "data : $favoriteData")
                setupAdapter(favoriteData)
            } else {
                Log.d("FavoriteFragment", "data : $favoriteData")
                binding.tvFavoriteEmpty.isVisible = true
            }
        }

    }

    private fun showGetFavoriteDataError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        binding.tvFavoriteEmpty.isVisible = true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFavorite.isVisible = isLoading
    }

    private fun setupAdapter(palateModel: List<GetFavoriteDataResponseItem?>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFavorite.layoutManager = layoutManager

        val favoriteAdapter = FavoriteAdapter()
        binding.rvFavorite.adapter = favoriteAdapter
        favoriteAdapter.submitList(palateModel)
    }
}