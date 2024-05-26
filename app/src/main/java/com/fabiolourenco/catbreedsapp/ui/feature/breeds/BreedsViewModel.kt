package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val getBreedsResultFlow = MutableStateFlow<GetBreedsResult>(GetBreedsResult.Initial)
    val getBreedsResultObservable: StateFlow<GetBreedsResult> = getBreedsResultFlow

    fun fetchBreeds() {
        viewModelScope.launch {
            getBreedsResultFlow.value = GetBreedsResult.Loading
            try {
                val breeds = repository.getBreeds()
                if (breeds.isEmpty()) {
                    getBreedsResultFlow.value = GetBreedsResult.Empty
                } else {
                    getBreedsResultFlow.value = GetBreedsResult.Success(breeds)
                }
            } catch (error: Exception) {
                Timber.e(error)
                getBreedsResultFlow.value = GetBreedsResult.Error("Error while fetching breeds")
            }
        }
    }

    fun searchBreeds(breedName: String) {
        viewModelScope.launch {
            getBreedsResultFlow.value = GetBreedsResult.Loading
            try {
                val breeds = repository.searchBreedsByName(breedName)
                if (breeds.isEmpty()) {
                    getBreedsResultFlow.value = GetBreedsResult.EmptySearchResult(breedName)
                } else {
                    getBreedsResultFlow.value = GetBreedsResult.Success(breeds)
                }
            } catch (error: Exception) {
                Timber.e(error)
                getBreedsResultFlow.value = GetBreedsResult.Error("Error while fetching breeds")
            }
        }
    }

    fun resetGetBreedsResult() {
        getBreedsResultFlow.value = GetBreedsResult.Initial
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