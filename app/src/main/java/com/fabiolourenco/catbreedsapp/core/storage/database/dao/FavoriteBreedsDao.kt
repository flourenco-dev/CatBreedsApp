package com.fabiolourenco.catbreedsapp.core.storage.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBreedsDao {

    @Query("SELECT * FROM FavoriteBreedEntity")
    suspend fun getAllFavoriteBreeds(): List<FavoriteBreedEntity>

    @Query("SELECT * FROM FavoriteBreedEntity")
    fun getAllFavoriteBreedsObservable(): Flow<List<FavoriteBreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBreed(breed: FavoriteBreedEntity)

    @Delete
    suspend fun deleteFavoriteBreed(breed: FavoriteBreedEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM FavoriteBreedEntity WHERE id = :id)")
    suspend fun exists(id: String): Boolean
}