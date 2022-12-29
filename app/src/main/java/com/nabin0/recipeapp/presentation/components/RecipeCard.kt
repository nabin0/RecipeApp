package com.nabin0.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.nabin0.recipeapp.domain.Recipe
import com.nabin0.recipeapp.util.DEFAULT_IMAGE
import com.nabin0.recipeapp.util.ImageUtils

@Composable
fun RecipeCard(
    recipe: Recipe, onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                top = 6.dp, bottom = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            recipe.featureImage?.let { url ->
                val image = ImageUtils.loadImage(url = url, defaultImage = DEFAULT_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(225.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = recipe.rating.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }

    }
}












