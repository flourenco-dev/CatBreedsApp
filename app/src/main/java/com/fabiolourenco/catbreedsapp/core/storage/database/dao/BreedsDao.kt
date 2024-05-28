package com.fabiolourenco.catbreedsapp.core.storage.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedsDao {

    @Query("SELECT * FROM BreedEntity")
    suspend fun getAllBreeds(): List<BreedEntity>

    @Query("SELECT * FROM BreedEntity")
    fun getAllBreedsObservable(): Flow<List<BreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breeds: List<BreedEntity>)

    @Delete
    suspend fun deleteAll(breeds: List<BreedEntity>)

    @Query("SELECT * FROM BreedEntity WHERE id = :id")
    suspend fun getBreedById(id: String): BreedEntity

    @Query("SELECT * FROM BreedEntity WHERE name LIKE '%' || :name || '%'")
    suspend fun getBreedsByName(name: String): List<BreedEntity>

    @Query("SELECT * FROM BreedEntity WHERE name LIKE '%' || :name || '%'")
    fun getBreedsByNameObservable(name: String): Flow<List<BreedEntity>>

    @Query("SELECT * FROM BreedEntity WHERE id IN (:ids)")
    suspend fun getBreedsByIds(ids: List<String>): List<BreedEntity>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(breed: BreedEntity)

    @Query("DELETE FROM BreedEntity")
    suspend fun clearAll()
}