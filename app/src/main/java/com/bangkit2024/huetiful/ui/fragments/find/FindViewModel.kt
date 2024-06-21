package com.bangkit2024.huetiful.ui.fragments.find

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.remote.response.PredictPairResponse
import com.bangkit2024.huetiful.data.repository.PredictPairRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.SocketTimeoutException

class FindViewModel(
    private val predictPairRepository: PredictPairRepository
) : ViewModel() {

    private val _predictPairState = MutableStateFlow<Result<String>>(Result.Loading)
    val predictPairState: StateFlow<Result<String>> = _predictPairState

    private val _predictPairResult = MutableLiveData<PredictPairResponse?>()
    val predictPairResult: LiveData<PredictPairResponse?> = _predictPairResult

    fun predictPair(file: File, palateList: List<String>) {
        viewModelScope.launch {
            _predictPairState.value = Result.Loading

            val requestBody = palateList.toString().toRequestBody("text/plain".toMediaType())
            val imageFile = file.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                file.name,
                imageFile
            )

            try {
                val predictPairResponse = predictPairRepository.predictPair(multipartBody, requestBody)

                withContext(Dispatchers.Main) {
                    if (predictPairResponse != null && predictPairResponse.error == null) {
                        _predictPairResult.value = predictPairResponse
                        _predictPairState.emit(Result.Success("predict pair : success"))
                    } else {
                        _predictPairState.emit(Result.Error(predictPairResponse.error.toString()))
                        _predictPairResult.value = null
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _predictPairResult.value = null
                    if (e is SocketTimeoutException) {
                        _predictPairState.emit(Result.Error("failed to connect to server"))
                    } else {
                        _predictPairState.emit(Result.Error(e.message.toString()))
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "FindViewModel"
    }
}