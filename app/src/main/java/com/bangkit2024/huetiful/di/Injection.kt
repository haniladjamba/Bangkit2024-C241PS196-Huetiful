package com.bangkit2024.huetiful.di

import android.content.Context
import com.bangkit2024.huetiful.data.pref.UserPreference
import com.bangkit2024.huetiful.data.pref.datastore
import com.bangkit2024.huetiful.data.remote.retrofit.ApiConfig
import com.bangkit2024.huetiful.data.repository.AuthRepository
import com.bangkit2024.huetiful.data.repository.PreferenceRepository

object Injection {
    fun providePrefRepository(context: Context) : PreferenceRepository {
        val pref = UserPreference.getInstance(context.datastore)
        return PreferenceRepository.getInstance(pref)
    }

    fun provideAuthRepository() : AuthRepository {
        val authApiService = ApiConfig.getAuthApiService()
        return AuthRepository.getInstance(authApiService)
    }
}