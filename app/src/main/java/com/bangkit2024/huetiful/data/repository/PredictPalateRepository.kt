package com.bangkit2024.huetiful.data.repository

import com.bangkit2024.huetiful.data.remote.response.PredictPalateResponse
import com.bangkit2024.huetiful.data.remote.retrofit.ApiService
import okhttp3.MultipartBody

class PredictPalateRepository(
    private val apiService: ApiService
) {

    suspend fun predictPalate(file: MultipartBody.Part) : PredictPalateResponse {
        return apiService.predictPalate(file)
    }

    companion object{
        @Volatile
        private var INSTANCE: PredictPalateRepository? = null
        fun getInstance(apiService: ApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PredictPalateRepository(apiService)
            }.also { INSTANCE = it }
    }
}