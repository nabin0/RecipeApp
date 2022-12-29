package com.nabin0.recipeapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CircularIndeterminateProgressBar(
    isDisplay: Boolean,
    modifier: Modifier
) {
    if (isDisplay) {
        Row(
            modifier = modifier
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}