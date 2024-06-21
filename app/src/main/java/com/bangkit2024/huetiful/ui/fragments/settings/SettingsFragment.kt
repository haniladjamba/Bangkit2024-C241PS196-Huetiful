package com.bangkit2024.huetiful.ui.fragments.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.data.utils.awaitFirstValue
import com.bangkit2024.huetiful.databinding.FragmentSettingsBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.AuthViewModelFactory
import com.bangkit2024.huetiful.ui.utils.setLocale
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!
    private val settingsViewModel by viewModels<SettingsViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSwitchStatus()
        observeLanguageStatus()
        setupAction()
        setupLanguageSelection()
    }

    private fun observeSwitchStatus() {
        lifecycleScope.launch {
            val isDarkMode = settingsViewModel.getThemeSetting().awaitFirstValue()
            isDarkMode?.let { binding.swChangeTheme.isChecked = it }
        }
    }

    private fun setupAction() {
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
        binding.btnLogout.setOnClickListener {
            createLogoutDialog()
        }
    }

    private fun createLogoutDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_logout)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.dialog_background, null))
        dialog.setCancelable(false)

        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btn_cancel)
        val btnConfirmLogout = dialog.findViewById<MaterialButton>(R.id.btn_confirm_logout)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnConfirmLogout.setOnClickListener {
            settingsViewModel.logout()
            activity?.finishAffinity()
        }

        dialog.show()
    }

    private fun setupLanguageSelection() {
        val radioGroup = binding.radioGroup
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_switch_id -> {
                    setLocale(requireContext(), "id")
                    settingsViewModel.saveLanguageSetting(false)
                }
                R.id.rb_switch_en -> {
                    setLocale(requireContext(), "en")
                    settingsViewModel.saveLanguageSetting(true)
                }
            }
        }
    }

    private fun observeLanguageStatus() {
        lifecycleScope.launch {
            val isEnglish = settingsViewModel.getCurrentLanguage().awaitFirstValue()
            isEnglish?.let { language ->
                if (language) {
                    binding.rbSwitchEn.isChecked = true
                } else {
                    binding.rbSwitchId.isChecked = true
                }
            }
        }
    }

}