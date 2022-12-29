package com.nabin0.recipeapp.presentation.ui.recipelist

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabin0.recipeapp.domain.Recipe
import com.nabin0.recipeapp.presentation.BaseApplication
import com.nabin0.recipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    @Named("token") private val token: String,
    app: Application
) : AndroidViewModel(app) {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Int = 0

    var loading = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    init {
        searchRecipe()
    }

    fun searchRecipe() {
        loading.value = true
        clearSearchState()

        viewModelScope.launch {
            try {
                if (hasInternetConnection()) {
                    val response = repository.searchRecipe(
                        token = token,
                        page = 1,
                        query = query.value
                    )
                    recipes.value = response
                } else {
                    Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT)
                        .show()
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
            loading.value = false
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            try {
                if (hasInternetConnection()) {
                    if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                        loading.value = true

                        incrementPage()

                        if (page.value > 1) {

                            val result = repository.searchRecipe(
                                token = token,
                                page = page.value,
                                query = query.value
                            )
                            appendRecipe(result)
                        }
                    }
                    loading.value = false
                } else {
                    Toast.makeText(
                        getApplication(),
                        "No Internet Connection",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    /**
     * Append new recipes to the previous list of recipes
     */
    private fun appendRecipe(recipes: List<Recipe>) {
        val currentList = ArrayList(this.recipes.value)
        currentList.addAll(recipes)
        this.recipes.value = currentList
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeRecipeListScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun clearSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeListScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onSearchQueryChanged(newQuery: String) {
        query.value = newQuery
    }


    fun onSelectedCategoryChanged(category: String) {
        selectedCategory.value = getFoodCategory(category)
        onSearchQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(newPosition: Int) {
        categoryScrollPosition = newPosition
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<BaseApplication>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_ETHERNET -> true
                    TYPE_MOBILE -> true
                    else -> false
                }
            }
        }
        return false
    }
}