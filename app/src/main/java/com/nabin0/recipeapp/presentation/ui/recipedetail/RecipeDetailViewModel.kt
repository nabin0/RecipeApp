package com.nabin0.recipeapp.presentation.ui.recipedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabin0.recipeapp.domain.Recipe
import com.nabin0.recipeapp.presentation.ui.recipedetail.RecipeDetailEvent.GetRecipeEvent
import com.nabin0.recipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeDetailViewModel
@Inject constructor(
    private val recipeRepository: RecipeRepository,
    @Named("token") private val token: String,
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    fun onTriggerEvent(event: RecipeDetailEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true

        val recipe = recipeRepository.getRecipe(token = token, id = id)
        this.recipe.value = recipe

        loading.value = false
    }

}