package com.bangkit2024.huetiful.ui.fragments

import android.view.View
import androidx.navigation.Navigation.findNavController
import com.bangkit2024.huetiful.R

class NavigationController() {
    fun navigatToFullDetailFragment(view: View) {
        val navController = findNavController(view) // Unresolved reference: view
        navController.navigate(R.id.action_navigation_result_to_navigation_fulldetail)
    }

    companion object {
        private val instance = NavigationController()
        fun getInstance() = instance
    }
}