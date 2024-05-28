package com.fabiolourenco.corestorage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fabiolourenco.corestorage.database.dao.BreedsDao
import com.fabiolourenco.corestorage.database.dao.FavoriteBreedsDao
import com.fabiolourenco.corestorage.database.entity.BreedEntity
import com.fabiolourenco.corestorage.database.entity.FavoriteBreedEntity

@Database(
    entities = [
        BreedEntity::class,
        FavoriteBreedEntity::class
    ],
    version = 1
)
abstract class CatBreedsDatabase : RoomDatabase() {
    abstract fun breedsDao(): BreedsDao
    abstract fun favoriteBreedsDao(): FavoriteBreedsDao
}