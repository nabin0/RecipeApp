package com.nabin0.recipeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCategoryChip(
    isSelected: Boolean,
    category: String,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecutionSearch: () -> Unit
) {
    AssistChip(
        onClick = {
            onSelectedCategoryChanged(category)
            onExecutionSearch()
        },
        modifier = Modifier.padding(end = 8.dp),
        colors = if (isSelected) {
            AssistChipDefaults.assistChipColors(containerColor = Color.Gray)
        } else {
            AssistChipDefaults.assistChipColors()
        },
        label = {
            Text(text = category)
        })
}
