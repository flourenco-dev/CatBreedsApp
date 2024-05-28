package com.fabiolourenco.uifeaturefavorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabiolourenco.common.uiModel.CatBreed
import com.fabiolourenco.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val favoriteBreedsFlow = repository.getFavoriteBreedsObservable()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
    val favoriteBreedsObservable: StateFlow<List<CatBreed>> = favoriteBreedsFlow

    fun removeFavoriteBreed(breed: CatBreed) {
        viewModelScope.launch {
            repository.removeFavoriteBreed(breed)
        }
    }

    fun getAverageLifeSpan(favoriteBreeds: List<CatBreed>): Double =
        favoriteBreeds.mapNotNull { it.lifeSpan }.average()
}