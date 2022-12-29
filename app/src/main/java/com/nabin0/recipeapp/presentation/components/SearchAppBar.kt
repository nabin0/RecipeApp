package com.nabin0.recipeapp.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nabin0.recipeapp.R
import com.nabin0.recipeapp.presentation.ui.recipelist.FoodCategory
import com.nabin0.recipeapp.presentation.ui.recipelist.getAllFoodCategories

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    query: String,
    categoryScrollPosition: Int,
    selectedCategory: FoodCategory?,
    keyboardController: SoftwareKeyboardController?,
    onSearchQueryChanged: (String) -> Unit,
    searchRecipe: () -> Unit,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    onToggleAppTheme: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = { newQuery ->
                        onSearchQueryChanged(newQuery)
                    },
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "Search")
                    },
                    keyboardActions = KeyboardActions(onSearch = {
                        searchRecipe()
                        keyboardController?.hide()

                    }),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                IconButton(
                    onClick = { onToggleAppTheme() },
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                ) {
                    Icon(painter = painterResource(R.drawable.theme_mode) ,
                        contentDescription = "Toggle Theme",
                    modifier = Modifier.fillMaxSize(0.9f))
                }

            }
            val categoryScrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .horizontalScroll(categoryScrollState)
                    .fillMaxWidth(),
            ) {
                LaunchedEffect(categoryScrollState) {
                    categoryScrollState.scrollTo(categoryScrollPosition)
                }
                for (category in getAllFoodCategories()) {
                    FoodCategoryChip(
                        isSelected = selectedCategory == category,
                        category = category.value,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                            onChangeCategoryScrollPosition(categoryScrollState.value)
                        },
                        onExecutionSearch = {
                            searchRecipe()
                        }
                    )
                }
            }

        }
    }
}