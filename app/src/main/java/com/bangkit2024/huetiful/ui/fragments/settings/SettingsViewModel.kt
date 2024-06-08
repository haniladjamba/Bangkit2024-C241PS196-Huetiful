package com.bangkit2024.huetiful.ui.fragments.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.repository.PreferenceRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val _closeApp = MutableLiveData<Boolean>()
    val closeApp: LiveData<Boolean> = _closeApp

    fun logout() {
        viewModelScope.launch {
            preferenceRepository.logout()
        }
        _closeApp.value = true
    }
}