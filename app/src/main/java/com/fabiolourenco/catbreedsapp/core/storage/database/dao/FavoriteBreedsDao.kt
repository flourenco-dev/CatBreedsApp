package com.fabiolourenco.catbreedsapp.core.storage.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity

@Dao
interface FavoriteBreedsDao {
    @Query("SELECT * FROM FavoriteBreedEntity")
    suspend fun getAllFavoriteBreeds(): List<FavoriteBreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBreed(breed: FavoriteBreedEntity)

    @Delete
    suspend fun deleteFavoriteBreed(breed: FavoriteBreedEntity)
}