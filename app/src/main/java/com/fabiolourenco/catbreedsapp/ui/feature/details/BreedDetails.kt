package com.fabiolourenco.catbreedsapp.ui.feature.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabiolourenco.catbreedsapp.R

@Composable
fun BreedDetails(
    viewModel: DetailsViewModel = hiltViewModel(),
    breedId: String = "",
    onCloseClick: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.resetGetBreedByIdResult()
        viewModel.fetchBreedById(breedId)
    }
    val breedResult = viewModel.getBreedByIdResultObservable.collectAsState().value
    Surface {
        when (breedResult) {
            is GetBreedByIdResult.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is GetBreedByIdResult.Success -> {
                BreedDetailsInfo(
                    breed = breedResult.breed,
                    onFavoriteClick = {
                        viewModel.updateFavoriteBreed(breed = it)
                    },
                    onCloseClick = {
                        onCloseClick()
                        viewModel.resetGetBreedByIdResult()
                    }
                )
            }
            is GetBreedByIdResult.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.breeds_error_message),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {}
        }
    }
}