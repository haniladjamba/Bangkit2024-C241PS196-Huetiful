package com.bangkit2024.huetiful.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
