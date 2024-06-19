package com.bangkit2024.huetiful.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictPairResponse(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("dominant_color")
	val dominantColor: String? = null,

	@field:SerializedName("predicted_color")
	val predictedColor: String? = null
)
