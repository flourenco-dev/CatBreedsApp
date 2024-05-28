package com.fabiolourenco.core.injection

import com.fabiolourenco.core.Repository
import com.fabiolourenco.core.RepositoryImpl
import com.fabiolourenco.corenetwork.ApiHelper
import com.fabiolourenco.corestorage.StorageHelper
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