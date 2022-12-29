package com.nabin0.recipeapp.network.model

import com.nabin0.recipeapp.domain.Recipe
import com.nabin0.recipeapp.domain.util.DtoMapper

class RecipeDtoMapper : DtoMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.primaryKey,
            title = model.title,
            publisher = model.publisher,
            featureImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceUrl,
            description = model.description,
            cookingInstruction = model.cookingInstruction,
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated,
            ingredients = model.ingredients ?: listOf()
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            primaryKey = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featureImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstruction = domainModel.cookingInstruction,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated,
            ingredients = domainModel.ingredients ?: listOf()
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map { mapFromDomainModel(it) }
    }

}