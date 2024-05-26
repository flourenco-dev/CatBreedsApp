package com.fabiolourenco.catbreedsapp.ui.feature.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiolourenco.catbreedsapp.common.utils.UiState
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

    private val breedsStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val breedsStateObservable: StateFlow<UiState> = breedsStateFlow

    fun fetchBreeds() {
        viewModelScope.launch {
            breedsStateFlow.value = UiState.Loading
            try {
                val breeds = repository.getCatBreeds()
                breedsStateFlow.value = UiState.Success(breeds)
            } catch (error: Exception) {
                Timber.e(error)
                breedsStateFlow.value = UiState.Error("Error while fetching breeds")
            }
        }
    }
}