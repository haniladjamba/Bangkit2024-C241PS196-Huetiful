package com.bangkit2024.huetiful.data.repository

import android.util.Log
import com.bangkit2024.huetiful.data.remote.response.LoginResponse
import com.bangkit2024.huetiful.data.remote.response.RegisterResponse
import com.bangkit2024.huetiful.data.remote.response.ResetPasswordResponse
import com.bangkit2024.huetiful.data.remote.retrofit.AuthApiService

class AuthRepository private constructor(
    private val authApiService: AuthApiService
){
    suspend fun register(name: String, email: String, password: String) : RegisterResponse {
        Log.d("repository", "email: $email")
        return authApiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String) : LoginResponse {
        return authApiService.login(email, password)
    }

    suspend fun resetPassword(email: String) : ResetPasswordResponse {
        return authApiService.resetPassword(email)
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null
        fun getInstance(authApiService: AuthApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthRepository(authApiService)
            }.also { INSTANCE = it }
    }
}