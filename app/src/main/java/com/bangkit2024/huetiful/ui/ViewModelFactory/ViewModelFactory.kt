package com.bangkit2024.huetiful.ui.ViewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.huetiful.data.repository.PredictPalateRepository
import com.bangkit2024.huetiful.di.Injection
import com.bangkit2024.huetiful.ui.fragments.home.HomeViewModel

class ViewModelFactory(
    private val predictPalateRepository: PredictPalateRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(predictPalateRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
//        @JvmStatic
//        fun getInstance(context: Context) : AuthViewModelFactory {
//            val authRepository = Injection.provideAuthRepository()
//            val preferenceRepository = Injection.providePrefRepository(context)
//            val settingsPreferenceRepository = Injection.provideSettingRepository(context)
//            if (AuthViewModelFactory.INSTANCE == null) {
//                synchronized(AuthViewModelFactory::class.java) {
//                    AuthViewModelFactory.INSTANCE = AuthViewModelFactory(authRepository, preferenceRepository, settingsPreferenceRepository)
//                }
//            }
//            return AuthViewModelFactory.INSTANCE as AuthViewModelFactory
//        }

        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context) : ViewModelFactory {
            val predictPalateRepository = Injection.providePredictPalateRepository()

            if (ViewModelFactory.INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    ViewModelFactory.INSTANCE = ViewModelFactory(predictPalateRepository)
                }
            }
            return ViewModelFactory.INSTANCE as ViewModelFactory
        }
    }
}