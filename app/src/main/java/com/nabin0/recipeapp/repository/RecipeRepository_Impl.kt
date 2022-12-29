package com.nabin0.recipeapp.repository

import com.nabin0.recipeapp.domain.Recipe
import com.nabin0.recipeapp.network.RecipeService
import com.nabin0.recipeapp.network.model.RecipeDtoMapper

class RecipeRepository_Impl(
    private val recipeService: RecipeService,
    private val domainMapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun searchRecipe(token: String, page: Int, query: String): List<Recipe> {
        return domainMapper.toDomainList(recipeService.searchRecipe(token, page, query).recipes)
    }

    override suspend fun getRecipe(token: String, id: Int): Recipe {
        return domainMapper.mapToDomainModel(recipeService.getRecipe(token, id))
    }
}