package com.fabiolourenco.uifeaturebreeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiolourenco.common.uiModel.CatBreed
import com.fabiolourenco.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val breedsFlow = repository.getBreedsObservable()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
    val breedsObservable: StateFlow<List<CatBreed>> = breedsFlow

    private val fetchBreedsResultFlow = MutableStateFlow<FetchBreedsResult>(
        FetchBreedsResult.Initial
    )
    val fetchBreedsResultObservable: StateFlow<FetchBreedsResult> = fetchBreedsResultFlow

    private val breedsByNameFlow = MutableStateFlow<List<CatBreed>>(emptyList())
    val breedsByNameObservable: StateFlow<List<CatBreed>> = breedsByNameFlow

    private val fetchBreedsByNameResultFlow = MutableStateFlow<FetchBreedsByNameResult>(
        FetchBreedsByNameResult.Initial
    )
    val fetchBreedsByNameResultObservable: StateFlow<FetchBreedsByNameResult> =
        fetchBreedsByNameResultFlow

    fun fetchBreeds() {
        fetchBreedsResultFlow.value = FetchBreedsResult.Loading
        viewModelScope.launch {
            try {
                repository.fetchBreeds()
                fetchBreedsResultFlow.value = FetchBreedsResult.Success
            } catch (error: Exception) {
                Timber.e(error)
                fetchBreedsResultFlow.value = FetchBreedsResult.Error
            }
        }
    }

    fun resetFetchBreedsResult() {
        fetchBreedsResultFlow.value = FetchBreedsResult.Initial
    }

    fun searchBreeds(breedName: String) {
        viewModelScope.launch {
            repository.getBreedsByNameObservable(breedName).collect {
                breedsByNameFlow.value = it
            }
            fetchBreedsByNameResultFlow.value = FetchBreedsByNameResult.Loading
            try {
                repository.fetchBreedsByName(breedName)
                fetchBreedsByNameResultFlow.value = FetchBreedsByNameResult.Success
            } catch (error: Exception) {
                Timber.e(error)
                fetchBreedsByNameResultFlow.value = FetchBreedsByNameResult.Error(breedName)
            }
        }
    }

    fun resetFetchBreedsByNameResult() {
        fetchBreedsByNameResultFlow.value = FetchBreedsByNameResult.Initial
    }

    fun updateFavoriteBreed(breed: CatBreed) {
        viewModelScope.launch {
            if (breed.isFavorite) {
                repository.removeFavoriteBreed(breed)
            } else {
                repository.addFavoriteBreed(breed)
            }
        }
    }
}