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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StorageModule::class]
)
object TestStorageModule {

    @Singleton
    @Provides
    fun provideCatBreedsDatabase(@ApplicationContext context: Context): CatBreedsDatabase =
        Room.inMemoryDatabaseBuilder(context, CatBreedsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

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