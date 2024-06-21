package com.bangkit2024.huetiful.ui.activity.resetpassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class ResetPasswordViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _resetPasswordState = MutableStateFlow<Result<String>>(Result.Loading)
    val resetPasswordState: StateFlow<Result<String>> = _resetPasswordState

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _resetPasswordState.value = Result.Loading
            try {
                val resetPasswordResponse = authRepository.resetPassword(email)
                if (resetPasswordResponse.status == "success") {
                    _resetPasswordState.emit(Result.Success(resetPasswordResponse.message))
                } else {
                    _resetPasswordState.emit(Result.Error(resetPasswordResponse.message))
                }
            } catch (e: Exception) {
                Log.d(TAG, "reset password error : ${e.message.toString()}")
                if (e is SocketTimeoutException) {
                    _resetPasswordState.emit(Result.Error("failed to connect to server"))
                } else {
                    _resetPasswordState.emit(Result.Error(e.message.toString()))
                }
            }
        }
    }

    companion object {
        const val TAG = "ResetPasswordViewModel"
    }
}