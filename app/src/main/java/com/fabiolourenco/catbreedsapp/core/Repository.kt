package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getBreeds(): List<CatBreed>
    suspend fun searchBreedsByName(breedName: String): List<CatBreed>
    suspend fun addFavoriteBreed(breed: CatBreed)
    suspend fun removeFavoriteBreed(breed: CatBreed)
    fun getFavoriteBreeds(): Flow<List<CatBreed>>
    suspend fun getBreedById(breedsId: String): CatBreed
}