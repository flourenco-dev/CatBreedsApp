package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed

sealed class GetBreedsResult {
    data object Initial : GetBreedsResult()
    data object Loading : GetBreedsResult()
    data object Empty : GetBreedsResult()
    data class Success(val breeds: List<CatBreed>) : GetBreedsResult()
    data class EmptySearchResult(val breedName: String) : GetBreedsResult()
    data class Error(val message: String) : GetBreedsResult()
}