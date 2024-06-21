package com.bangkit2024.huetiful.data.repository

import com.bangkit2024.huetiful.data.pref.SettingsPreference
import kotlinx.coroutines.flow.Flow

class SettingPreferenceRepository private constructor(
    private val settingsPreference: SettingsPreference
){

    fun getThemeSetting(): Flow<Boolean> {
        return settingsPreference.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        settingsPreference.saveThemeSetting(isDarkMode)
    }

    suspend fun saveLangSetting(isEnglish: Boolean) {
        settingsPreference.saveLangSetting(isEnglish)
    }

    fun getCurrentLanguage(): Flow<Boolean> {
        return settingsPreference.getCurrentLanguage()
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferenceRepository? = null
        fun getInstance( settingsPreference: SettingsPreference) : SettingPreferenceRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingPreferenceRepository(settingsPreference)
            }.also { INSTANCE = it }
    }
}