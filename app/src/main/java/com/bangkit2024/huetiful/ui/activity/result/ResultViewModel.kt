package com.bangkit2024.huetiful.ui.activity.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.remote.response.Name
import com.bangkit2024.huetiful.data.repository.ColorInfoRepository
import com.bangkit2024.huetiful.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResultViewModel(
    private val favoriteRepository: FavoriteRepository,
    private val colorInfoRepository: ColorInfoRepository
) : ViewModel() {

    private val _saveFavoriteState = MutableStateFlow<Result<String>>(Result.Loading)
    val saveFavoriteState: StateFlow<Result<String>> = _saveFavoriteState

    private val _getColorNameState = MutableStateFlow<Result<String>>(Result.Loading)
    val getColorNameState: StateFlow<Result<String>> = _getColorNameState

    private val _colorName = MutableLiveData<Name>()
    val colorName: LiveData<Name> = _colorName

    fun saveFavoritePalate(exractedSkinTone: String, predictedPalate: List<String>) {
        viewModelScope.launch {
            _saveFavoriteState.value = Result.Loading

            try {
                val saveFavoriteResponse = favoriteRepository.saveFavoritePalate(exractedSkinTone, predictedPalate)
                if (saveFavoriteResponse != null && saveFavoriteResponse.msg == "Activity saved successfully") {
                    _saveFavoriteState.emit(Result.Success(R.string.item_saved.toString()))
                } else {
                    _saveFavoriteState.emit(Result.Error("failed to save items"))
                }
            } catch (e: Exception) {
                Log.d(TAG, "exception : ${e.message.toString()}")
                _saveFavoriteState.emit(Result.Error("error : ${e.message.toString()}"))
            }
        }
    }

    fun getColorName(hex: String) {
        viewModelScope.launch {
            _getColorNameState.value =  Result.Loading

            Log.d("ResultViewModel", " hex data :$hex")
            try {
                val getColorNameResponse = colorInfoRepository.getColorName(hex)
                val colorName = getColorNameResponse.name
                if (colorName != null) {
                    Log.d(TAG, "color name2: ${colorName.value}")
                    _getColorNameState.emit(Result.Success(colorName.value.toString()))
                    _colorName.value = colorName
                } else {
                    Log.d(TAG, "color name: unknown")
                    _getColorNameState.emit(Result.Error("Unknown"))
                    _colorName.value = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "error geting color name : ${e.message.toString()}")
                _getColorNameState.emit(Result.Error("Unknown"))
                _colorName.value = null
            }
        }
    }

    companion object {
        const val TAG = "ResultViewModel"
    }
}