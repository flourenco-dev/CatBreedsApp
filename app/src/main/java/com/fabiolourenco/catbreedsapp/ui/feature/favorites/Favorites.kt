package com.fabiolourenco.catbreedsapp.ui.feature.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabiolourenco.catbreedsapp.R
import com.fabiolourenco.catbreedsapp.common.ui.components.BreedsGrid
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed

@Composable
fun Favorites(
    viewModel: FavoritesViewModel = hiltViewModel(),
    goToBreedsDetails: (CatBreed) -> Unit = {}
) {
    val favoriteBreeds = viewModel.favoriteBreedsObservable.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        if (favoriteBreeds.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.favorites_empty_message),
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(
                        R.string.favorites_life_span_text,
                        "%.1f".format(viewModel.getAverageLifeSpan())
                    )
                )
            }
            BreedsGrid(
                breeds = favoriteBreeds.value,
                onFavoriteClick = {
                    viewModel.removeFavoriteBreed(breed = it)
                },
                onItemClick = {
                    goToBreedsDetails(it)
                }
            )
        }
    }
}