package com.fabiolourenco.catbreedsapp.ui.feature.details

import com.fabiolourenco.common.uiModel.CatBreed

sealed class GetBreedByIdResult {
    data object Initial : GetBreedByIdResult()
    data object Loading : GetBreedByIdResult()
    data class Success(val breed: CatBreed) : GetBreedByIdResult()
    data class Error(val message: String) : GetBreedByIdResult()
}