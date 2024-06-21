package com.bangkit2024.huetiful.di

import android.content.Context
import android.util.Log
import com.bangkit2024.huetiful.BuildConfig
import com.bangkit2024.huetiful.data.pref.SettingsPreference
import com.bangkit2024.huetiful.data.pref.UserPreference
import com.bangkit2024.huetiful.data.pref.datastore
import com.bangkit2024.huetiful.data.remote.retrofit.ApiConfig
import com.bangkit2024.huetiful.data.repository.AuthRepository
import com.bangkit2024.huetiful.data.repository.ColorInfoRepository
import com.bangkit2024.huetiful.data.repository.FavoriteRepository
import com.bangkit2024.huetiful.data.repository.PredictPairRepository
import com.bangkit2024.huetiful.data.repository.PredictPalateRepository
import com.bangkit2024.huetiful.data.repository.PreferenceRepository
import com.bangkit2024.huetiful.data.repository.SettingPreferenceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun providePrefRepository(context: Context) : PreferenceRepository {
        val pref = UserPreference.getInstance(context.datastore)
        return PreferenceRepository.getInstance(pref)
    }

    fun provideSettingRepository(context: Context) : SettingPreferenceRepository {
        val settingPref = SettingsPreference.getInstance(context.datastore)
        return SettingPreferenceRepository.getInstance(settingPref)
    }

    fun provideAuthRepository() : AuthRepository {
        val authApiService = ApiConfig.getAuthApiService()
        return AuthRepository.getInstance(authApiService)
    }

    fun providePredictPalateRepository() : PredictPalateRepository {
        val apiService = ApiConfig.getApiService(BuildConfig.BASE_URL_MODEL1)
        return PredictPalateRepository.getInstance(apiService)
    }

    fun providePredictPairRepository() : PredictPairRepository {
        val apiService = ApiConfig.getApiService(BuildConfig.BASE_URL_MODEL2)
        return PredictPairRepository.getInstance(apiService)
    }

    fun provideFavoriteRepository(context: Context) : FavoriteRepository {
        val pref = UserPreference.getInstance(context.datastore)
        val user = runBlocking { pref.getSession().first() }
        Log.d("Injection", "token : ${user.token}")
        val favoriteApiService = ApiConfig.getFavoriteApiService(user.token)
        return FavoriteRepository.getInstance(favoriteApiService)
    }

    fun provideColorInfoRepository() : ColorInfoRepository {
        val colorApiService = ApiConfig.getColorApiService()
        return ColorInfoRepository.getInstance(colorApiService)
    }
}