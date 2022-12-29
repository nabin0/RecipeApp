package com.nabin0.recipeapp.network.responses

import com.google.gson.annotations.SerializedName
import com.nabin0.recipeapp.network.model.RecipeDto

data class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("next")
    var next: String? = null,

    @SerializedName("previous")
    var previous: String? = null,

    @SerializedName("results")
    var recipes: List<RecipeDto>
)
