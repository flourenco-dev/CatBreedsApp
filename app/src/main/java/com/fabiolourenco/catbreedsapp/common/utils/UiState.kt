package com.fabiolourenco.catbreedsapp.common.utils

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed

sealed class UiState {
    data object Initial : UiState()
    data object Loading : UiState()
    data class Success(val breeds: List<CatBreed>) : UiState()
    data class Error(val message: String) : UiState()
}