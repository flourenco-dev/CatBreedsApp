package com.fabiolourenco.catbreedsapp.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed

@Composable
fun BreedCard(
    breed: CatBreed,
    onFavoriteClick: (CatBreed) -> Unit
) {
    val isBreedFavorite = remember { mutableStateOf(breed.isFavorite) }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(breed.imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (name, favoriteButton) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Black.copy(alpha = 0.5f)
                    )
                    .constrainAs(name) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text(
                    text = breed.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )
            }
            IconButton(
                modifier = Modifier.constrainAs(favoriteButton) {
                    top.linkTo(parent.top, 2.dp)
                    end.linkTo(parent.end, 2.dp)
                },
                onClick = {
                    onFavoriteClick(
                        breed.copy(isFavorite = isBreedFavorite.value)
                    )
                    isBreedFavorite.value = !isBreedFavorite.value
                }
            ) {
                Icon(
                    imageVector = if (isBreedFavorite.value) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = if (isBreedFavorite.value) {
                        "Unfavorite button"
                    } else {
                        "Favorite button"
                    }
                )
            }
        }
    }
}