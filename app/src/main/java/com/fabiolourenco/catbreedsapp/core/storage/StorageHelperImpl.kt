package com.fabiolourenco.catbreedsapp.core.storage

import com.fabiolourenco.catbreedsapp.core.storage.database.dao.FavoriteBreedsDao
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity
import javax.inject.Inject

internal class StorageHelperImpl @Inject constructor(
    private val favoriteBreedsDao: FavoriteBreedsDao
) : StorageHelper {

    override suspend fun getAllFavoriteBreeds(): List<FavoriteBreedEntity> =
        favoriteBreedsDao.getAllFavoriteBreeds()

    override suspend fun addFavoriteBreed(breed: FavoriteBreedEntity) =
        favoriteBreedsDao.insertFavoriteBreed(breed)

    override suspend fun removeFavoriteBreed(breed: FavoriteBreedEntity) =
        favoriteBreedsDao.deleteFavoriteBreed(breed)
}