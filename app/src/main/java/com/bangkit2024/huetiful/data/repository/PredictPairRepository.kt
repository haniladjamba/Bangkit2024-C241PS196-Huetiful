package com.bangkit2024.huetiful.data.repository

import com.bangkit2024.huetiful.data.remote.response.PredictPairResponse
import com.bangkit2024.huetiful.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PredictPairRepository(
    private val apiService: ApiService
) {

    suspend fun predictPair(file: MultipartBody.Part, requestBody: RequestBody) : PredictPairResponse {
        return apiService.predictPair(file, requestBody)
    }

    companion object{
        @Volatile
        private var INSTANCE: PredictPairRepository? = null
        fun getInstance(apiService: ApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PredictPairRepository(apiService)
            }.also { INSTANCE = it }
    }
}