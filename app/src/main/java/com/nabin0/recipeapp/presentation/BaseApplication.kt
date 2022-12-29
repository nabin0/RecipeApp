package com.nabin0.recipeapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    // Store in shared pref
    val isDarkThemeApplied = mutableStateOf(false)

    fun toggleAppTheme() {
        isDarkThemeApplied.value = !isDarkThemeApplied.value
    }
}