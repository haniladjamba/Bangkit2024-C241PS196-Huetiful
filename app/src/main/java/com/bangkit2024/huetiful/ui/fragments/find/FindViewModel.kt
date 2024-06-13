package com.bangkit2024.huetiful.ui.fragments.find

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.remote.response.PredictPairResponse
import com.bangkit2024.huetiful.data.repository.PredictPalateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FindViewModel(
    private val predictPalateRepository: PredictPalateRepository
) : ViewModel() {

    private val _predictPairState = MutableStateFlow<Result<String>>(Result.Loading)
    val predictPairState: StateFlow<Result<String>> = _predictPairState

    private val _predictPairResult = MutableLiveData<PredictPairResponse?>()
    val predictPairResult: LiveData<PredictPairResponse?> = _predictPairResult

    fun predictPair(file: File) {
        viewModelScope.launch {
            _predictPairState.value = Result.Loading

            val imageFile = file.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                file.name,
                imageFile
            )

            try {
                val predictPairResponse = predictPalateRepository.predictPair(multipartBody)

                if (predictPairResponse != null && predictPairResponse.error == null) {
                    _predictPairResult.value = predictPairResponse
                    _predictPairState.emit(Result.Success("predict pair : success"))
                } else {
                    _predictPairState.emit(Result.Error(predictPairResponse.error.toString()))
                    _predictPairResult.value = null
                }
            } catch (e: Exception) {
                _predictPairState.emit(Result.Error(e.message.toString()))
                _predictPairResult.value = null
                Log.d(TAG, "predictPair error : ${e.message.toString()}")
            }
        }
    }

    companion object {
        const val TAG = "FindViewModel"
    }
}