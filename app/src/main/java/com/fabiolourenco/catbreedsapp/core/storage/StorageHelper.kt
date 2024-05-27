package com.fabiolourenco.catbreedsapp.core.storage

import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity
import kotlinx.coroutines.flow.Flow

// Storage abstraction to hide implementation details from Repository and allow possible changes of
// implementation with minimum impact
interface StorageHelper {
    suspend fun getAllFavoriteBreeds(): List<FavoriteBreedEntity>
    fun getAllFavoriteBreedsObservable(): Flow<List<FavoriteBreedEntity>>
    suspend fun addFavoriteBreed(breed: FavoriteBreedEntity)
    suspend fun removeFavoriteBreed(breed: FavoriteBreedEntity)
}