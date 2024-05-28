package com.fabiolourenco.catbreedsapp.ui.feature.paginatedBreeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fabiolourenco.catbreedsapp.core.Repository
import com.fabiolourenco.common.uiModel.CatBreed
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class PaginatedBreedsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val pageSize = 5
    // Paging was decided to be implemented separated to for simplicity purposes and just as a PoC,
    // otherwise it would be required to deal with possible deleted Cat Breeds from the server that
    // could be still stored in the database, adding more complexity to the solution

    val breedsPage: Flow<PagingData<CatBreed>> = Pager(
        config = PagingConfig(pageSize = pageSize)
    ) {
        BreedsPagingSource(repository = repository, pageSize = pageSize)
    }.flow.cachedIn(viewModelScope)
}