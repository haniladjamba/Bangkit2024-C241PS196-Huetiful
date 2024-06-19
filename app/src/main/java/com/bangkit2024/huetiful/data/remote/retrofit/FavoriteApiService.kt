package com.bangkit2024.huetiful.data.remote.retrofit

import com.bangkit2024.huetiful.data.remote.response.GetFavoriteDataResponseItem
import com.bangkit2024.huetiful.data.remote.response.SaveFavoriteResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FavoriteApiService {

    @FormUrlEncoded
    @POST("api/save-activity")
    suspend fun saveFavorite(
        @Field("extracted_skin_tone") extractedSkinTone: String,
        @Field("predicted_palette") predictedPalette: List<String>
    ) : SaveFavoriteResponse

    @GET("api/activities")
    suspend fun getFavoriteData() : List<GetFavoriteDataResponseItem>
}