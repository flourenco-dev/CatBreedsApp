package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.common.uiModel.CatBreed
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getBreedsObservable(): Flow<List<CatBreed>>
    suspend fun fetchBreeds()
    fun getBreedsByNameObservable(breedName: String): Flow<List<CatBreed>>
    suspend fun fetchBreedsByName(breedName: String)
    suspend fun addFavoriteBreed(breed: CatBreed)
    suspend fun removeFavoriteBreed(breed: CatBreed)
    fun getFavoriteBreedsObservable(): Flow<List<CatBreed>>
    suspend fun getBreedById(breedId: String): CatBreed
    suspend fun getBreedsPage(page: Int, limit: Int): List<CatBreed>
}