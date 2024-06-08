package com.bangkit2024.huetiful.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.databinding.FragmentSettingsBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.AuthViewModelFactory

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val settingsViewModel by viewModels<SettingsViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // uncomment this line when api service is available
//        setupAction()
    }

    private fun setupAction() {
        binding.btnLogout.setOnClickListener {
            settingsViewModel.logout()
        }
    }
}