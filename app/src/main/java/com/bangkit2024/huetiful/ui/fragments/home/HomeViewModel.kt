package com.bangkit2024.huetiful.ui.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.repository.PredictPalateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class HomeViewModel(
private val predictPalateRepository: PredictPalateRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _predictPalateState = MutableStateFlow<Result<String>>(Result.Loading)
    val predictPalateState: StateFlow<Result<String>> = _predictPalateState

    private val _predictPalateResult = MutableLiveData<List<String?>?>()
    val predictPalateResult: LiveData<List<String?>?> = _predictPalateResult

    fun predictPalate(file: File) {
        viewModelScope.launch {
            _predictPalateState.value = Result.Loading

            val imageFile = file.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                file.name,
                imageFile
            )

            try {
                val predictPalateResponse = predictPalateRepository.predictPalate(multipartBody)

                if (predictPalateResponse != null && predictPalateResponse.error == null) {
                    _predictPalateResult.value = predictPalateResponse.predictedPalette
                    _predictPalateState.emit(Result.Success("predictPalate : success"))
                } else {
                    _predictPalateResult.value = null
                    _predictPalateState.emit(Result.Error("${predictPalateResponse.error}"))
                }
            } catch (e: Exception) {
                _predictPalateResult.value = null
                _predictPalateState.emit(Result.Error(e.message.toString()))
                Log.d(TAG, "predictPalate error : ${e.message.toString()}")
            }
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}