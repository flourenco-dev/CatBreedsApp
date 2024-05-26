package com.fabiolourenco.catbreedsapp.core.injection

import com.fabiolourenco.catbreedsapp.core.Repository
import com.fabiolourenco.catbreedsapp.core.RepositoryImpl
import com.fabiolourenco.catbreedsapp.core.network.ApiHelper
import com.fabiolourenco.catbreedsapp.core.storage.StorageHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideRepository(
        apiHelper: ApiHelper,
        storageHelper: StorageHelper
    ): Repository =
        RepositoryImpl(
            apiHelper = apiHelper,
            storageHelper = storageHelper
        )
}