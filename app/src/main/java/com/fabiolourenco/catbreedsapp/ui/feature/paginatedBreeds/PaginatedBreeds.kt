package com.fabiolourenco.catbreedsapp.ui.feature.paginatedBreeds

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fabiolourenco.catbreedsapp.R
import com.fabiolourenco.catbreedsapp.common.ui.components.BreedCard
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PaginatedBreeds(
    viewModel: PaginatedBreedsViewModel = hiltViewModel()
) {
    val breedsPages: LazyPagingItems<CatBreed> = viewModel.breedsPage.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    val isRefreshing by remember {
        derivedStateOf { breedsPages.loadState.refresh is LoadState.Loading }
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = swipeRefreshState,
        onRefresh = {
            breedsPages.refresh()
        }
    ) {
        LazyColumn(state = listState) {
            items(count = breedsPages.itemCount) { index ->
                breedsPages[index]?.let { breed ->
                    BreedCard(breed = breed, onFavoriteClick = {}, onClick = {})
                }
            }
            breedsPages.loadState.let { loadState ->
                when {
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(
                                        R.string.paging_breeds_initial_error_message
                                    )
                                )
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.paging_breeds_page_error_message)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}