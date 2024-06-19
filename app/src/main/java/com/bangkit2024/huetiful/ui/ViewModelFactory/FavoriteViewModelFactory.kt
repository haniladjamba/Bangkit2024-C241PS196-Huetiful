package com.bangkit2024.huetiful.ui.ViewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.huetiful.data.repository.FavoriteRepository
import com.bangkit2024.huetiful.di.Injection
import com.bangkit2024.huetiful.ui.activity.result.ResultViewModel
import com.bangkit2024.huetiful.ui.fragments.favorite.FavoriteViewModel

class FavoriteViewModelFactory(
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(favoriteRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(favoriteRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        private var INSTANCE: FavoriteViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context) : FavoriteViewModelFactory {
            val favoriteRepository = Injection.provideFavoriteRepository(context)

            if (INSTANCE == null) {
                synchronized(AuthViewModelFactory::class.java) {
                    INSTANCE = FavoriteViewModelFactory(favoriteRepository)
                }
            }
            return INSTANCE as FavoriteViewModelFactory
        }
    }
}