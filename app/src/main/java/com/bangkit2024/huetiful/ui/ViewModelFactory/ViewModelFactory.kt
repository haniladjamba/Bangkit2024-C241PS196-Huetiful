package com.bangkit2024.huetiful.ui.ViewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.huetiful.data.repository.PredictPalateRepository
import com.bangkit2024.huetiful.di.Injection
import com.bangkit2024.huetiful.ui.fragments.find.FindViewModel
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
            modelClass.isAssignableFrom(FindViewModel::class.java) -> {
                FindViewModel(predictPalateRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance() : ViewModelFactory {
            val predictPalateRepository = Injection.providePredictPalateRepository()

            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(predictPalateRepository)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}