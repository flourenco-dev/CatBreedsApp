package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getBreeds(): List<CatBreed>
    fun getBreedsObservable(): Flow<List<CatBreed>>
    suspend fun fetchBreeds()
    suspend fun searchBreedsByName(breedName: String): List<CatBreed>
    fun getBreedsByNameObservable(breedName: String): Flow<List<CatBreed>>
    suspend fun fetchBreedsByName(breedName: String)
    suspend fun addFavoriteBreed(breed: CatBreed)
    suspend fun removeFavoriteBreed(breed: CatBreed)
    fun getFavoriteBreedsObservable(): Flow<List<CatBreed>>
    suspend fun getBreedById(breedId: String): CatBreed
}