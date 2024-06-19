package com.bangkit2024.huetiful.data.repository

import com.bangkit2024.huetiful.data.remote.response.GetFavoriteDataResponse
import com.bangkit2024.huetiful.data.remote.response.GetFavoriteDataResponseItem
import com.bangkit2024.huetiful.data.remote.response.SaveFavoriteResponse
import com.bangkit2024.huetiful.data.remote.retrofit.FavoriteApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteRepository(
    private val favoriteApiService: FavoriteApiService
) {

    suspend fun saveFavoritePalate(extractedSkinTone: String, predictedPalate: List<String>) : SaveFavoriteResponse {
        return favoriteApiService.saveFavorite(extractedSkinTone, predictedPalate)
    }

    suspend fun getFavoriteData() : List<GetFavoriteDataResponseItem> {
        return favoriteApiService.getFavoriteData()
    }

    companion object{
        @Volatile
        private var INSTANCE: FavoriteRepository? = null
        fun getInstance(favoriteApiService: FavoriteApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteRepository(favoriteApiService)
            }.also { INSTANCE = it }
    }
}