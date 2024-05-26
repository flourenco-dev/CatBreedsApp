package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.common.utils.UiState

@Composable
fun Breeds(viewModel: BreedsViewModel = hiltViewModel()) {
    val breedsState = viewModel.breedsStateObservable.collectAsState().value
    if (breedsState is UiState.Initial) {
        viewModel.fetchBreeds()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (breedsState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                LazyColumn {
                    items(breedsState.breeds) { breed ->
                        CatBreedItem(breed)
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = breedsState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {}
        }
    }
}

@Composable
fun CatBreedItem(breed: CatBreed) {
    Row(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = rememberAsyncImagePainter(breed.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = breed.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = breed.origin,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}