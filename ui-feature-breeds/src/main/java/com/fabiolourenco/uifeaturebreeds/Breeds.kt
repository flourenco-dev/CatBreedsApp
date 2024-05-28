package com.fabiolourenco.uifeaturebreeds

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabiolourenco.common.R
import com.fabiolourenco.common.components.BreedsGrid
import com.fabiolourenco.common.uiModel.CatBreed

@Composable
fun Breeds(
    viewModel: BreedsViewModel = hiltViewModel(),
    goToBreedsDetails: (CatBreed) -> Unit = {}
) {
    val allBreeds = viewModel.breedsObservable.collectAsState().value
    val fetchBreedsResult = viewModel.fetchBreedsResultObservable.collectAsState().value
    val breedsByName = viewModel.breedsByNameObservable.collectAsState().value
    val fetchBreedsByNameResult = viewModel.fetchBreedsByNameResultObservable.collectAsState().value

    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    val searchText = rememberSaveable { mutableStateOf("") }

    Column {
        BreedSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            searchQuery = searchQuery.value,
            onSearchQueryChange = { query ->
                searchQuery.value = query
            },
            onExecuteSearch = {
                if (searchQuery.value.text.isEmpty()) {
                    searchText.value = ""
                    viewModel.fetchBreeds()
                } else {
                    searchText.value = searchQuery.value.text
                    viewModel.searchBreeds(searchQuery.value.text)
                }
            },
            onClearClicked = {
                searchText.value = ""
                viewModel.fetchBreeds()
            }
        )
        if (
            fetchBreedsResult == FetchBreedsResult.Loading ||
            fetchBreedsByNameResult == FetchBreedsByNameResult.Loading
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(1.dp))
        }
        if (searchText.value.isNotEmpty()) {
            when (fetchBreedsByNameResult) {
                is FetchBreedsByNameResult.Success -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.breeds_success_message),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetFetchBreedsByNameResult()
                }
                is FetchBreedsByNameResult.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(
                            R.string.breeds_error_search_result_message,
                            fetchBreedsByNameResult.breedName
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetFetchBreedsByNameResult()
                }
                else -> {}
            }
            if (breedsByName.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(
                            R.string.breeds_empty_search_result_message,
                            searchText.value
                        ),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else {
                BreedsGrid(
                    breeds = breedsByName,
                    onFavoriteClick = {
                        viewModel.updateFavoriteBreed(breed = it)
                    },
                    onItemClick = {
                        goToBreedsDetails(it)
                    }
                )
            }
        } else {
            when (fetchBreedsResult) {
                is FetchBreedsResult.Success -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.breeds_success_message),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetFetchBreedsResult()
                }
                is FetchBreedsResult.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.breeds_error_message),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetFetchBreedsResult()
                }
                else -> {}
            }
            if (allBreeds.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.breeds_empty_message),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else {
                BreedsGrid(
                    breeds = allBreeds,
                    onFavoriteClick = {
                        viewModel.updateFavoriteBreed(breed = it)
                    },
                    onItemClick = {
                        goToBreedsDetails(it)
                    }
                )
            }
        }
    }
}