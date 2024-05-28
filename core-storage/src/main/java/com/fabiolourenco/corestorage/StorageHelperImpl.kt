package com.fabiolourenco.corestorage

import com.fabiolourenco.corestorage.database.dao.BreedsDao
import com.fabiolourenco.corestorage.database.dao.FavoriteBreedsDao
import com.fabiolourenco.corestorage.database.entity.BreedEntity
import com.fabiolourenco.corestorage.database.entity.FavoriteBreedEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class StorageHelperImpl @Inject constructor(
    private val breedsDao: BreedsDao,
    private val favoriteBreedsDao: FavoriteBreedsDao
) : StorageHelper {

    override suspend fun getAllBreeds(): List<BreedEntity> = breedsDao.getAllBreeds()

    override fun getAllBreedsObservable(): Flow<List<BreedEntity>> =
        breedsDao.getAllBreedsObservable()

    override suspend fun addBreeds(breeds: List<BreedEntity>) = breedsDao.insertAll(breeds)

    override suspend fun removeBreeds(breeds: List<BreedEntity>) = breedsDao.deleteAll(breeds)

    override suspend fun getBreedById(breedId: String): BreedEntity =
        breedsDao.getBreedById(id = breedId)

    override suspend fun getBreedsByName(name: String): List<BreedEntity> =
        breedsDao.getBreedsByName(name)

    override fun getBreedsByNameObservable(name: String): Flow<List<BreedEntity>> =
        breedsDao.getBreedsByNameObservable(name)

    override suspend fun getBreedsByIds(ids: List<String>): List<BreedEntity> =
        breedsDao.getBreedsByIds(ids)

    override suspend fun updateBreed(breed: BreedEntity) = breedsDao.update(breed)

    override suspend fun getAllFavoriteBreeds(): List<FavoriteBreedEntity> =
        favoriteBreedsDao.getAllFavoriteBreeds()

    override fun getAllFavoriteBreedsObservable(): Flow<List<FavoriteBreedEntity>> =
        favoriteBreedsDao.getAllFavoriteBreedsObservable()

    override suspend fun addFavoriteBreed(breed: FavoriteBreedEntity) =
        favoriteBreedsDao.insertFavoriteBreed(breed)

    override suspend fun removeFavoriteBreed(breed: FavoriteBreedEntity) =
        favoriteBreedsDao.deleteFavoriteBreed(breed)

    override suspend fun isFavoriteBreed(breedId: String): Boolean =
        favoriteBreedsDao.exists(id = breedId)
}