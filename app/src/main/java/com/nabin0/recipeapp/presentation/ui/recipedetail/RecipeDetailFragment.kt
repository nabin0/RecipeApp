package com.nabin0.recipeapp.presentation.ui.recipedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nabin0.recipeapp.presentation.BaseApplication
import com.nabin0.recipeapp.presentation.components.LoadingRecipeDetailShimmer
import com.nabin0.recipeapp.presentation.components.RecipeDetailView
import com.nabin0.recipeapp.presentation.theme.AppTheme
import com.nabin0.recipeapp.presentation.ui.recipedetail.RecipeDetailEvent.GetRecipeEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val IMAGE_HEIGHT = 260

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value

                val recipe = viewModel.recipe.value


                AppTheme(darkTheme = application.isDarkThemeApplied.value) {

                    Box(modifier = Modifier.fillMaxSize()) {
                        if (loading && recipe == null) {
                            LoadingRecipeDetailShimmer(imageHeight = IMAGE_HEIGHT.dp)
                        } else {
                            recipe?.let { recipe ->
                                RecipeDetailView(recipe = recipe)
                            }
                        }
                    }
                }
            }
        }
    }
}