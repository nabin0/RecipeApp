package com.nabin0.recipeapp.di

import com.nabin0.recipeapp.network.RecipeService
import com.nabin0.recipeapp.network.model.RecipeDtoMapper
import com.nabin0.recipeapp.repository.RecipeRepository
import com.nabin0.recipeapp.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        domainMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepository_Impl(recipeService, domainMapper)
    }
}