package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed

@Composable
fun BreedsGrid(breeds: List<CatBreed>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(breeds) { breed ->
            BreedCard(breed)
        }
    }
}