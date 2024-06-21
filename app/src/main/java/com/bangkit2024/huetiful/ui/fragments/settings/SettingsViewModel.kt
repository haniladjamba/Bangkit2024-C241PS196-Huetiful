package com.bangkit2024.huetiful.ui.fragments.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.repository.PreferenceRepository
import com.bangkit2024.huetiful.data.repository.SettingPreferenceRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val settingPreferenceRepository: SettingPreferenceRepository
) : ViewModel() {

    private val _closeApp = MutableLiveData<Boolean>()
    val closeApp: LiveData<Boolean> = _closeApp

    fun logout() {
        viewModelScope.launch {
            preferenceRepository.logout()
        }
        _closeApp.value = true
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return settingPreferenceRepository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingPreferenceRepository.saveThemeSetting(isDarkMode)
        }
    }

    fun saveLanguageSetting(isEnglish: Boolean) {
        viewModelScope.launch {
            settingPreferenceRepository.saveLangSetting(isEnglish)
        }
    }

    fun getCurrentLanguage(): LiveData<Boolean> {
        return settingPreferenceRepository.getCurrentLanguage().asLiveData()
    }
}