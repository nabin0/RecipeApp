package com.nabin0.recipeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultSnackBar(
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    SnackbarHost(
        hostState = snackBarHostState,
        snackbar = { snackBarData ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    snackBarData.visuals.actionLabel?.let { actionLabel ->
                        TextButton(onClick = {
                            onDismiss()
                        }) {
                            Text(
                                text = actionLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }
                }
            ) {
                Text(
                    text = snackBarData.visuals.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        },
        modifier = modifier
    )
}