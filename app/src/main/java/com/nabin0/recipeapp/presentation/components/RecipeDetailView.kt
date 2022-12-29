package com.nabin0.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.nabin0.recipeapp.domain.Recipe
import com.nabin0.recipeapp.presentation.ui.recipedetail.IMAGE_HEIGHT
import com.nabin0.recipeapp.util.DEFAULT_IMAGE
import com.nabin0.recipeapp.util.ImageUtils.Companion.loadImage

@Composable
fun RecipeDetailView(
    recipe: Recipe
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surface),

        ) {
        recipe.featureImage?.let { url ->
            val image = loadImage(url = url, defaultImage = DEFAULT_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(), contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IMAGE_HEIGHT.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.titleLarge
                    )

                    recipe.rating?.let { rating ->
                        Text(
                            text = "$rating",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            recipe.publisher?.let { publisher ->
                val updated = recipe.dateUpdated
                Text(
                    text = if (updated != null) {
                        "Updated $updated by $publisher"
                    } else {
                        "by $publisher"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }

            for (ingredient in recipe.ingredients) {
                Text(
                    text = ingredient,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}