package com.bangkit2024.huetiful.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("predicted_palette")
	val predictedPalette: List<String?>? = null,

	@field:SerializedName("extracted_skin_tone")
	val extractedSkinTone: String? = null
)
