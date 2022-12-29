package com.nabin0.recipeapp.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nabin0.recipeapp.presentation.BaseApplication
import com.nabin0.recipeapp.presentation.RecipeList
import com.nabin0.recipeapp.presentation.components.*
import com.nabin0.recipeapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    private val snackBarController = SnackBarController(lifecycleScope)

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = application.isDarkThemeApplied.value) {
                    val query = viewModel.query.value

                    val recipes = viewModel.recipes.value

                    val keyboardController = LocalSoftwareKeyboardController.current

                    val selectedCategory = viewModel.selectedCategory.value

                    val loading = viewModel.loading.value

                    val snackbarHostState = remember { SnackbarHostState() }

                    val page = viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                categoryScrollPosition = viewModel.categoryScrollPosition,
                                selectedCategory = selectedCategory,
                                keyboardController = keyboardController,
                                onSearchQueryChanged = viewModel::onSearchQueryChanged,
                                searchRecipe = {
                                    if (viewModel.selectedCategory.value?.value == "Plastic") {
                                        snackBarController.getScope().launch {
                                            snackBarController.showSnackBar(
                                                snackBarHostState = snackbarHostState,
                                                message = "Invalid Category",
                                                actionLabel = "DISMISS"
                                            )
                                        }
                                    } else {
                                        viewModel.searchRecipe()
                                    }
                                },
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleAppTheme = {
                                    application.toggleAppTheme()
                                }
                            )
                        },
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                    ) {
                        RecipeList(
                            padding = it,
                            loading = loading,
                            page = page,
                            snackbarHostState = snackbarHostState,
                            snackBarController = snackBarController,
                            navController = findNavController(),
                            recipes = recipes,
                            onChangeRecipeListScrollPosition = viewModel::onChangeRecipeListScrollPosition,
                            nextPage = viewModel::nextPage
                        )
                    }
                }
            }
        }
    }

}