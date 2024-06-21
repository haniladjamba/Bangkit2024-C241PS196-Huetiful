package com.bangkit2024.huetiful.ui.fragments.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.remote.response.GetFavoriteDataResponseItem
import com.bangkit2024.huetiful.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _getFavoriteDataState = MutableStateFlow<Result<String>>(Result.Loading)
    val getFavoriteDataState: StateFlow<Result<String>> = _getFavoriteDataState

    private val _favoriteData = MutableLiveData<List<GetFavoriteDataResponseItem?>>()
    val favoriteData: LiveData<List<GetFavoriteDataResponseItem?>> = _favoriteData

    fun getFavoriteData() {
        viewModelScope.launch {
            _getFavoriteDataState.value = Result.Loading

            Log.d(TAG, "attempting to getFavoriteDataResponse from api")
            try {
                val getFavoriteDataResponse = favoriteRepository.getFavoriteData()
                if (getFavoriteDataResponse != null) {
                    _favoriteData.value = getFavoriteDataResponse
                    _getFavoriteDataState.emit(Result.Success("success retrieve data"))
                    Log.d(TAG, "saved item response : $_favoriteData")
                } else {
                    Log.d(TAG, "saved item response : $getFavoriteDataResponse")
                    _getFavoriteDataState.emit(Result.Error("no saved item found"))
                    _favoriteData.value = null
                }
            } catch (e: Exception) {
                Log.d(TAG, "exception : ${e.message.toString()}")
                _favoriteData.value = null
                if (e is SocketTimeoutException) {
                    _getFavoriteDataState.emit(Result.Error("failed to connect to server"))
                } else {
                    _getFavoriteDataState.emit(Result.Error(e.message.toString()))
                }
            }
        }
    }

    companion object {
        const val TAG = "FavoriteViewModel"
    }
}