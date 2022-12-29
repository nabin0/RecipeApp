package com.nabin0.recipeapp.presentation.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackBarController(
    private val scope: CoroutineScope
) {
    private var snackBarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = scope

    fun showSnackBar(
        snackBarHostState: SnackbarHostState,
        message: String,
        actionLabel: String
    ) {
        if (snackBarJob == null) {
            snackBarJob = scope.launch {
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Short
                )
                cancelActiveJob()
            }
        } else {
            cancelActiveJob()
            snackBarJob = scope.launch {
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Short
                )
                cancelActiveJob()
            }
        }
    }

    private fun cancelActiveJob() {
        snackBarJob?.let { job ->
            job.cancel()
            snackBarJob = Job()
        }
    }
}