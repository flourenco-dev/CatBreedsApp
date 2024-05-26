package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabiolourenco.catbreedsapp.R

@Composable
fun Breeds(viewModel: BreedsViewModel = hiltViewModel()) {
    val breedsResult = viewModel.getBreedsResultObservable.collectAsState().value
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    if (breedsResult is GetBreedsResult.Initial) {
        viewModel.fetchBreeds()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            BreedSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(8.dp),
                searchQuery = searchQuery.value,
                onSearchQueryChange = {
                        query ->
                    searchQuery.value = query
                },
                onExecuteSearch = {
                    if (searchQuery.value.text.isEmpty()) {
                        viewModel.fetchBreeds()
                    } else {
                        viewModel.searchBreeds(searchQuery.value.text)
                    }
                },
                onClearClicked = {
                    viewModel.fetchBreeds()
                }
            )
            when (breedsResult) {
                is GetBreedsResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is GetBreedsResult.Success -> {
                    BreedsGrid(
                        breeds = breedsResult.breeds,
                        onFavoriteClick = {
                            viewModel.updateFavoriteBreed(breed = it)
                        }
                    )
                }
                is GetBreedsResult.Error -> {
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
                is GetBreedsResult.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.breeds_empty_message),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                is GetBreedsResult.EmptySearchResult -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(
                                R.string.breeds_empty_search_result_message,
                                breedsResult.breedName
                            ),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                else -> {}
            }
        }
    }
}