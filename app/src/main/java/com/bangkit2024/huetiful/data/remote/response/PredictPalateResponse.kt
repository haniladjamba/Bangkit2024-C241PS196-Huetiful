package com.bangkit2024.huetiful.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictPalateResponse(

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("predicted_palette")
	val predictedPalette: List<String?> = emptyList(),

	@field:SerializedName("extracted_skin_tone")
	val extractedSkinTone: String? = null
)
