package com.bangkit2024.huetiful.data.remote.retrofit

import com.bangkit2024.huetiful.data.remote.response.ColorInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ColorApiService {

    @GET("/id")
    suspend fun getColorName(
        @Query("hex") hex: String
    ) : ColorInfoResponse

}