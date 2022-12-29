package com.nabin0.recipeapp.presentation.ui.recipedetail

sealed class RecipeDetailEvent {

    data class GetRecipeEvent(val id: Int) : RecipeDetailEvent()

}