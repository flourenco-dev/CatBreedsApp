package com.fabiolourenco.catbreedsapp.core.storage.injection

import android.content.Context
import androidx.room.Room
import com.fabiolourenco.catbreedsapp.core.storage.StorageHelper
import com.fabiolourenco.catbreedsapp.core.storage.StorageHelperImpl
import com.fabiolourenco.catbreedsapp.core.storage.database.CatBreedsDatabase
import com.fabiolourenco.catbreedsapp.core.storage.database.dao.BreedsDao
import com.fabiolourenco.catbreedsapp.core.storage.database.dao.FavoriteBreedsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideCatBreedsDatabase(@ApplicationContext context: Context): CatBreedsDatabase =
        Room.databaseBuilder(
            context,
            CatBreedsDatabase::class.java,
            "CatBreedsApp_db"
        ).build()

    @Provides
    @Singleton
    fun provideBreedsDao(database: CatBreedsDatabase): BreedsDao =
        database.breedsDao()

    @Provides
    @Singleton
    fun provideFavoriteBreedsDao(database: CatBreedsDatabase): FavoriteBreedsDao =
        database.favoriteBreedsDao()

    @Provides
    @Singleton
    fun provideStorageHelper(
        breedsDao: BreedsDao,
        favoriteBreedsDao: FavoriteBreedsDao
    ): StorageHelper =
        StorageHelperImpl(
            breedsDao = breedsDao,
            favoriteBreedsDao = favoriteBreedsDao
        )
}