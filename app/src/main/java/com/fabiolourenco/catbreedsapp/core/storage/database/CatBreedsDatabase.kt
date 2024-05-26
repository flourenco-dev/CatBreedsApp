package com.fabiolourenco.catbreedsapp.core.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fabiolourenco.catbreedsapp.core.storage.database.dao.FavoriteBreedsDao
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity

@Database(
    entities = [FavoriteBreedEntity::class],
    version = 1
)
abstract class CatBreedsDatabase : RoomDatabase() {
    abstract fun favoriteBreedsDao(): FavoriteBreedsDao
}