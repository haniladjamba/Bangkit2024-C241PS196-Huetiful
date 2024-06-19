//package com.bangkit2024.huetiful.data.pref
//
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import com.bangkit2024.huetiful.data.local.model.CurrentPalateModel
//
//class UserCurrentPalate private constructor(private val datastore: DataStore<Preferences>) {
//
//    suspend fun saveUserPalate(palateModel: CurrentPalateModel) {
//        datastore.edit { currentPalate ->
//            currentPalate[SKIN_TONE] = palateModel.skinTone
//        }
//        palateModel.skinTone.forEachIndexed { index, string ->
//            datastore.edit { currentPalate ->
//                currentPalate[CURRENT_PALATE + index] = string
//            }
//        }
//    }
//
//    companion object {
//        private var INSTANCE: UserCurrentPalate? = null
//
//        private var SKIN_TONE = stringPreferencesKey("skinTone")
//        private var CURRENT_PALATE = stringPreferencesKey("current_palate")
//
//        fun getInstance(datastore: DataStore<Preferences>) : UserCurrentPalate {
//            return INSTANCE ?: synchronized(this) {
//                val instance = UserCurrentPalate(datastore)
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}