package com.bangkit2024.huetiful.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictPairResponse(

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("chosen_color")
	val chosenColor: String? = null,

	@field:SerializedName("predicted_color")
	val predictedColor: String? = null
)
