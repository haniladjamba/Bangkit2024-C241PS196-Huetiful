package com.bangkit2024.huetiful.ui.activity.result

import android.net.http.HttpException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ResultViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _saveFavoriteState = MutableStateFlow<Result<String>>(Result.Loading)
    val saveFavoriteState: StateFlow<Result<String>> = _saveFavoriteState

    fun saveFavoritePalate(exractedSkinTone: String, predictedPalate: List<String>) {
        viewModelScope.launch {
            _saveFavoriteState.value = Result.Loading

            Log.d(TAG, "extracted skin tone : $exractedSkinTone,  palate : $predictedPalate")
            try {
                val saveFavoriteResponse = favoriteRepository.saveFavoritePalate(exractedSkinTone, predictedPalate)
                if (saveFavoriteResponse != null && saveFavoriteResponse.msg == "Activity saved successfully") {
                    _saveFavoriteState.emit(Result.Success("items saved"))
                    Log.d(TAG, "respond successfull")
                } else {
                    _saveFavoriteState.emit(Result.Error("failed to save items"))
                }
            } catch (e: Exception) {
                Log.d(TAG, "exception : ${e.message.toString()}")
                Log.d(TAG, "hard error")
                _saveFavoriteState.emit(Result.Error("error : ${e.message.toString()}"))
            }
        }
    }

    companion object {
        const val TAG = "ResultViewModel"
    }
}