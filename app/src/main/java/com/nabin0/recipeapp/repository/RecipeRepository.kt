package com.nabin0.recipeapp.repository

import com.nabin0.recipeapp.domain.Recipe

interface RecipeRepository {

    suspend fun searchRecipe(token: String, page: Int, query: String): List<Recipe>

    suspend fun getRecipe(token: String, id: Int): Recipe
}