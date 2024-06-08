package com.bangkit2024.huetiful.ui.activity.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit2024.huetiful.data.pref.UserModel
import com.bangkit2024.huetiful.data.repository.PreferenceRepository

class WelcomeViewModel(
    private val preferenceRepository: PreferenceRepository
) : ViewModel(){
    fun getSession(): LiveData<UserModel> {
        return preferenceRepository.getSession().asLiveData()
    }
}