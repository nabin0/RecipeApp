package com.nabin0.recipeapp.network.model

import com.google.gson.annotations.SerializedName

data class RecipeDto(

    @SerializedName("pk") val primaryKey: Int? = null,

    @SerializedName("title") val title: String? = null,

    @SerializedName("publisher") val publisher: String? = null,

    @SerializedName("featured_image") val featuredImage: String? = null,

    @SerializedName("rating") val rating: Int? = 0,

    @SerializedName("source_url") val sourceUrl: String? = null,

    @SerializedName("description") val description: String? = null,

    @SerializedName("cooking_instruction") val cookingInstruction: String? = null,

    @SerializedName("ingredients") val ingredients: List<String>? = null,

    @SerializedName("date_added") val dateAdded: String? = null,

    @SerializedName("date_updated") val dateUpdated: String? = null,


    )