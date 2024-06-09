package com.bangkit2024.huetiful.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        observeSwitchStatus()
        setupAction()
    }

    private fun observeSwitchStatus() {
        settingsViewModel.getThemeSetting().observe(requireActivity()) { isDarkMode: Boolean ->
            if (isDarkMode) {
                binding.swChangeTheme.isChecked = true
            } else {
                binding.swChangeTheme.isChecked = false
            }
        }
    }

    private fun setupAction() {
        // uncomment when api service is available
//        binding.btnLogout.setOnClickListener {
//            settingsViewModel.logout()
//        }
        binding.swChangeTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                settingsViewModel.saveThemeSetting(isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swChangeTheme.isChecked = true
            } else {
                settingsViewModel.saveThemeSetting(isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swChangeTheme.isChecked = false
            }
        }
    }
}