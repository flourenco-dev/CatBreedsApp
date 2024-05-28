package com.fabiolourenco.core

import com.fabiolourenco.common.uiModel.CatBreed
import com.fabiolourenco.corenetwork.ApiHelper
import com.fabiolourenco.corenetwork.model.BreedModel
import com.fabiolourenco.corestorage.StorageHelper
import com.fabiolourenco.corestorage.database.entity.BreedEntity
import com.fabiolourenco.corestorage.database.entity.FavoriteBreedEntity
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val storageHelper: StorageHelper
) : Repository {

    override fun getBreedsObservable(): Flow<List<CatBreed>> =
        storageHelper.getAllBreedsObservable().map {
            it.toCatBreeds()
        }

    private fun List<BreedEntity>.toCatBreeds(): List<CatBreed> = map {
        it.toCatBreed()
    }

    private fun BreedEntity.toCatBreed(): CatBreed = CatBreed(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
        lifeSpan = lifeSpan,
        isFavorite = isFavorite
    )

    override suspend fun fetchBreeds() {
        // To provide offline support the database was used as single source of truth, so all data
        // is obtained from the database and then a request to update it is done

        // Get all remote breeds
        val remoteBreeds = apiHelper.getBreeds().map {
            it.toBreedEntity(
                isFavorite = storageHelper.isFavoriteBreed(it.id)
            )
        }
        // Update database with the latest breeds
        storageHelper.addBreeds(breeds = remoteBreeds)
        // Apply delete logic to ensure that local and remote breeds match
        val breedsToDelete = storageHelper.getAllBreeds().filter { localBreed ->
            localBreed.id !in remoteBreeds.map { it.id }
        }
        if (breedsToDelete.isNotEmpty()) {
            storageHelper.removeBreeds(breedsToDelete)
        }
    }

    private fun BreedModel.toBreedEntity(isFavorite: Boolean): BreedEntity = BreedEntity(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        imageUrl = image?.url,
        lifeSpan = lifeSpan.extractMaxLifeSpanValue(),
        isFavorite = isFavorite
    )

    private fun String?.extractMaxLifeSpanValue(): Int? =
        this?.split("-")?.lastOrNull()?.trim()?.toIntOrNull()

    override fun getBreedsByNameObservable(breedName: String): Flow<List<CatBreed>> =
        storageHelper.getBreedsByNameObservable(name = breedName).map {
            it.toCatBreeds()
        }

    override suspend fun fetchBreedsByName(breedName: String) {
        // Get all remote breeds
        val remoteBreeds = apiHelper.getBreedsByName(breedName).map {
            it.toBreedEntity(
                isFavorite = storageHelper.isFavoriteBreed(it.id)
            )
        }
        // Update database with the latest breeds
        storageHelper.addBreeds(breeds = remoteBreeds)
        // Apply delete logic to ensure that local and remote breeds match
        val breedsToDelete = storageHelper.getBreedsByName(name = breedName).filter { localBreed ->
            localBreed.id !in remoteBreeds.map { it.id }
        }
        if (breedsToDelete.isNotEmpty()) {
            storageHelper.removeBreeds(breedsToDelete)
        }
    }

    override suspend fun addFavoriteBreed(breed: CatBreed) {
        storageHelper.updateBreed(breed.toBreedEntity(isFavorite = true))
        storageHelper.addFavoriteBreed(breed.toFavoriteBreedEntity())
    }

    private fun CatBreed.toBreedEntity(isFavorite: Boolean): BreedEntity = BreedEntity(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
        lifeSpan = lifeSpan,
        isFavorite = isFavorite
    )

    private fun CatBreed.toFavoriteBreedEntity(): FavoriteBreedEntity = FavoriteBreedEntity(
        id = id
    )

    override suspend fun removeFavoriteBreed(breed: CatBreed) {
        storageHelper.updateBreed(breed.toBreedEntity(isFavorite = false))
        storageHelper.removeFavoriteBreed(breed.toFavoriteBreedEntity())
    }

    override fun getFavoriteBreedsObservable(): Flow<List<CatBreed>> =
        storageHelper.getAllFavoriteBreedsObservable().map { favoriteBreedsList ->
            val favoriteIds = favoriteBreedsList.map { it.id }
            storageHelper.getBreedsByIds(favoriteIds).toCatBreeds()
        }

    override suspend fun getBreedById(breedId: String): CatBreed =
        storageHelper.getBreedById(breedId).toCatBreed()

    override suspend fun getBreedsPage(page: Int, limit: Int): List<CatBreed> {
        // Delay just to allow testing of loadings
        delay(3000)
        return apiHelper.getBreedsPage(page, limit).map {
            it.toCatBreed(
                isFavorite = storageHelper.isFavoriteBreed(breedId = it.id)
            )
        }
    }

    private fun BreedModel.toCatBreed(isFavorite: Boolean): CatBreed = CatBreed(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        imageUrl = image?.url,
        lifeSpan = lifeSpan.extractMaxLifeSpanValue(),
        isFavorite = isFavorite
    )
}