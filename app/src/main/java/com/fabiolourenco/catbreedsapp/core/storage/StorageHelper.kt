package com.fabiolourenco.catbreedsapp.core.storage

import com.fabiolourenco.catbreedsapp.core.storage.database.entity.BreedEntity
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity
import kotlinx.coroutines.flow.Flow

// Storage abstraction to hide implementation details from Repository and allow possible changes of
// implementation with minimum impact
interface StorageHelper {
    suspend fun getAllBreeds(): List<BreedEntity>
    fun getAllBreedsObservable(): Flow<List<BreedEntity>>
    suspend fun addBreeds(breeds: List<BreedEntity>)
    suspend fun removeBreeds(breeds: List<BreedEntity>)
    suspend fun getBreedById(breedId: String): BreedEntity
    suspend fun getBreedsByName(name: String): List<BreedEntity>
    fun getBreedsByNameObservable(name: String): Flow<List<BreedEntity>>
    suspend fun getBreedsByIds(ids: List<String>): List<BreedEntity>
    suspend fun updateBreed(breed: BreedEntity)
    suspend fun getAllFavoriteBreeds(): List<FavoriteBreedEntity>
    fun getAllFavoriteBreedsObservable(): Flow<List<FavoriteBreedEntity>>
    suspend fun addFavoriteBreed(breed: FavoriteBreedEntity)
    suspend fun removeFavoriteBreed(breed: FavoriteBreedEntity)
    suspend fun isFavoriteBreed(breedId: String): Boolean
}