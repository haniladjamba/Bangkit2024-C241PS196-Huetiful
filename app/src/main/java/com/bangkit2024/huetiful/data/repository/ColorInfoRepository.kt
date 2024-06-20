package com.bangkit2024.huetiful.data.repository

import com.bangkit2024.huetiful.data.remote.response.ColorInfoResponse
import com.bangkit2024.huetiful.data.remote.retrofit.ColorApiService

class ColorInfoRepository private constructor(
    private val colorApiService: ColorApiService
) {

    suspend fun getColorName(hex: String) : ColorInfoResponse {
        return colorApiService.getColorName(hex)
    }

    companion object {
        @Volatile
        private var INSTANCE: ColorInfoRepository? = null
        fun getInstance(colorApiService: ColorApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ColorInfoRepository(colorApiService)
            }.also { INSTANCE = it }
    }
}