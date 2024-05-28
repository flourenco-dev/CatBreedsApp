package com.fabiolourenco.catbreedsapp.ui.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiolourenco.common.uiModel.CatBreed
import com.fabiolourenco.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val getBreedByIdResultFlow = MutableStateFlow<GetBreedByIdResult>(
        GetBreedByIdResult.Initial
    )
    val getBreedByIdResultObservable: StateFlow<GetBreedByIdResult> = getBreedByIdResultFlow

    fun fetchBreedById(breedId: String) {
        viewModelScope.launch {
            getBreedByIdResultFlow.value = GetBreedByIdResult.Loading
            try {
                val breed = repository.getBreedById(breedId)
                getBreedByIdResultFlow.value = GetBreedByIdResult.Success(breed)
            } catch (error: Exception) {
                Timber.e(error)
                getBreedByIdResultFlow.value =
                    GetBreedByIdResult.Error("Error while fetching breed by ID")
            }
        }
    }

    fun resetGetBreedByIdResult() {
        getBreedByIdResultFlow.value = GetBreedByIdResult.Initial
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