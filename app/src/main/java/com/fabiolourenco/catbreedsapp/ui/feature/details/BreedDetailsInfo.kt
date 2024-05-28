package com.fabiolourenco.catbreedsapp.ui.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.fabiolourenco.catbreedsapp.R
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed

@Composable
fun BreedDetailsInfo(
    breed: CatBreed,
    onFavoriteClick: (CatBreed) -> Unit = {}
) {
    val isBreedFavorite = remember { mutableStateOf(breed.isFavorite) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            val (
                image,
                name,
                favoriteButton,
                origin,
                temperament,
                description
            ) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(breed.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.ratio("1.5")
                    }
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = breed.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(image.bottom, 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(favoriteButton.start)
                        width = Dimension.fillToConstraints
                    },
                textAlign = TextAlign.Start
            )
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .constrainAs(favoriteButton) {
                        top.linkTo(name.top)
                        bottom.linkTo(name.bottom)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    onFavoriteClick(
                        breed.copy(isFavorite = isBreedFavorite.value)
                    )
                    isBreedFavorite.value = !isBreedFavorite.value
                }
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = if (isBreedFavorite.value) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = if (isBreedFavorite.value) {
                        "Unfavorite button"
                    } else {
                        "Favorite button"
                    },
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            BreedDetailsInfoItem(
                icon = painterResource(id = R.drawable.ic_location),
                info = "Origin: ${breed.origin}",
                modifier = Modifier.constrainAs(origin) {
                    top.linkTo(name.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            BreedDetailsInfoItem(
                icon = painterResource(id = R.drawable.ic_pet_paw),
                info = "Temperament: ${breed.temperament}",
                modifier = Modifier.constrainAs(temperament) {
                    top.linkTo(origin.bottom, 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Text(
                text = breed.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .constrainAs(description) {
                        top.linkTo(temperament.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 8.dp)
            )
        }
    }
}