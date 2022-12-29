package com.nabin0.recipeapp.presentation.ui.recipelist

enum class FoodCategory(val value: String) {
    CHICKEN("Chicken"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),
    BANANA("Banana"),
    BREAKFAST("Breakfast"),
    DINNER("Dinner"),
    PLASTIC("Plastic"),
}

fun getAllFoodCategories(): List<FoodCategory> {
    return FoodCategory.values().toList()
}

fun getFoodCategory(value: String): FoodCategory? {
    val map = FoodCategory.values().associateBy(FoodCategory::value)
    return map[value]
}