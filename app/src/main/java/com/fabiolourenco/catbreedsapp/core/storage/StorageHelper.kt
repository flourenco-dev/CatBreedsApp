package com.fabiolourenco.catbreedsapp.core.storage

import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity

// Storage abstraction to hide implementation details from Repository and allow possible changes of
// implementation with minimum impact
interface StorageHelper {
    suspend fun getAllFavoriteBreeds(): List<FavoriteBreedEntity>
    suspend fun addFavoriteBreed(breed: FavoriteBreedEntity)
    suspend fun removeFavoriteBreed(breed: FavoriteBreedEntity)
}