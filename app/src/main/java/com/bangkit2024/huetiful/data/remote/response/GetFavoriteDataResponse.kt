package com.bangkit2024.huetiful.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetFavoriteDataResponse(

	@field:SerializedName("GetFavoriteDataResponse")
	val getFavoriteDataResponse: List<GetFavoriteDataResponseItem?>? = emptyList()
)

data class GetFavoriteDataResponseItem(

	@field:SerializedName("predicted_palette")
	val predictedPalette: List<String>? = emptyList(),

	@field:SerializedName("email_users")
	val emailUsers: String? = null,

	@field:SerializedName("extracted_skin_tone")
	val extractedSkinTone: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
