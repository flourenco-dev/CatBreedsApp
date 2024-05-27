package com.fabiolourenco.catbreedsapp.ui.feature.breeds

sealed class FetchBreedsResult {
    data object Initial : FetchBreedsResult()
    data object Loading : FetchBreedsResult()
    data object Success : FetchBreedsResult()
    data object Error : FetchBreedsResult()
}