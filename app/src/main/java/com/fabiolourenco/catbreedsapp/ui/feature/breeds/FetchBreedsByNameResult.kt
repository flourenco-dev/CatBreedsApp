package com.fabiolourenco.catbreedsapp.ui.feature.breeds

sealed class FetchBreedsByNameResult {
    data object Initial : FetchBreedsByNameResult()
    data object Loading : FetchBreedsByNameResult()
    data object Success : FetchBreedsByNameResult()
    data class Error(val breedName: String) : FetchBreedsByNameResult()
}