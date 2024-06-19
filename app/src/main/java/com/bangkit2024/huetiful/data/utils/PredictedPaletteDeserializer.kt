//package com.bangkit2024.huetiful.data.utils
//
//import com.bangkit2024.huetiful.data.remote.response.GetFavoriteDataResponseItem
//import com.google.gson.JsonDeserializationContext
//import com.google.gson.JsonDeserializer
//import com.google.gson.JsonElement
//import com.google.gson.JsonSyntaxException
//import com.google.gson.reflect.TypeToken
//import java.lang.reflect.Type
//
//class PredictedPaletteDeserializer : JsonDeserializer<List<String>> {
//    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): GetFavoriteDataResponseItem {
//        val jsonObject = json?.asJsonObject ?: throw JsonSyntaxException("Expected JsonObject")
//
//        val id = jsonObject.get("id")?.asInt
//        val emailUsers = jsonObject.get("email_users")?.asString
//        val extractedSkinTone = jsonObject.get("extracted_skin_tone")?.asString
//
//        val predictedPaletteJson = jsonObject.get("predicted_palette")?.asString
//        val predictedPalette = if (!predictedPaletteJson.isNullOrEmpty()) {
//            predictedPaletteJson.trim('"').substring(1, predictedPaletteJson.length - 1).split(", ").map { it.trim() }
//        } else {
//            emptyList()
//        }
//
//        return GetFavoriteDataResponseItem(
//            predictedPalette = predictedPalette,
//            emailUsers = emailUsers,
//            extractedSkinTone = extractedSkinTone,
//            id = id
//        )
//    }
//}