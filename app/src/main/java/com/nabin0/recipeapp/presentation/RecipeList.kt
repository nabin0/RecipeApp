package com.nabin0.recipeapp.presentation

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nabin0.recipeapp.R
import com.nabin0.recipeapp.domain.Recipe
import com.nabin0.recipeapp.presentation.components.*
import com.nabin0.recipeapp.presentation.ui.recipelist.PAGE_SIZE
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    padding: PaddingValues,
    loading: Boolean,
    page: Int,
    snackbarHostState: SnackbarHostState,
    snackBarController: SnackBarController,
    navController: NavController,
    recipes: List<Recipe>,
    onChangeRecipeListScrollPosition: (Int) -> Unit,
    nextPage: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(items = recipes) { index, recipe ->
                    onChangeRecipeListScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE)) {
                        nextPage()
                    }
                    RecipeCard(recipe = recipe, onClick = {
                        if (recipe.id != null) {
                            val bundle = Bundle();
                            bundle.putInt("recipeId", recipe.id)
                            navController.navigate(
                                R.id.action_recipeListFragment_to_recipeDetailFragment,
                                bundle
                            )
                        } else {
                            snackBarController.getScope().launch {
                                snackBarController.showSnackBar(
                                    snackBarHostState = snackbarHostState,
                                    message = "Recipe Error",
                                    actionLabel = "Ok"
                                )
                            }
                        }
                    })
                }
            }
        }

        CircularIndeterminateProgressBar(
            isDisplay = loading && recipes.isNotEmpty(),
            modifier = Modifier.align(Alignment.Center)
        )
        DefaultSnackBar(
            snackBarHostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }
}