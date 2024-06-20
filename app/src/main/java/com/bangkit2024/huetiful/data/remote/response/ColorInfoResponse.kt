package com.bangkit2024.huetiful.data.remote.response

import com.google.gson.annotations.SerializedName

data class ColorInfoResponse(

	@field:SerializedName("name")
	val name: Name? = null
)

data class Name(

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("closest_named_hex")
	val closestNamedHex: String? = null
)
