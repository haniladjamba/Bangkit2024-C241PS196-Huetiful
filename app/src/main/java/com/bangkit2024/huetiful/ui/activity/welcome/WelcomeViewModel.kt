package com.bangkit2024.huetiful.ui.activity.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.pref.UserModel
import com.bangkit2024.huetiful.data.repository.PreferenceRepository
import com.bangkit2024.huetiful.data.repository.SettingPreferenceRepository
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val settingPreferenceRepository: SettingPreferenceRepository
) : ViewModel(){
    fun getSession(): LiveData<UserModel> {
        return preferenceRepository.getSession().asLiveData()
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return settingPreferenceRepository.getThemeSetting().asLiveData()
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