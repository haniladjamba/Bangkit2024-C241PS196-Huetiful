package com.bangkit2024.huetiful.data.remote.retrofit

import com.bangkit2024.huetiful.data.remote.response.PredictPalateResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("predict_palette")
    suspend fun predictPalate(
        @Part file: MultipartBody.Part
    ) : PredictPalateResponse

}