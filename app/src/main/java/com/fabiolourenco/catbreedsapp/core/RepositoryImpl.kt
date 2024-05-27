package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.core.network.ApiHelper
import com.fabiolourenco.catbreedsapp.core.network.model.BreedModel
import com.fabiolourenco.catbreedsapp.core.storage.StorageHelper
import com.fabiolourenco.catbreedsapp.core.storage.database.entity.FavoriteBreedEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val storageHelper: StorageHelper
) : Repository {

    override suspend fun getBreeds(): List<CatBreed> {
        val breedModels = apiHelper.getBreeds()
        val favoriteBreeds = storageHelper.getAllFavoriteBreeds()
        return breedModels.map { breedModel ->
            breedModel.toCatBreed(
                isFavorite = favoriteBreeds.any { it.id == breedModel.id }
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

    private fun String?.extractMaxLifeSpanValue(): Int? =
        this?.split("-")?.lastOrNull()?.trim()?.toIntOrNull()

    override suspend fun searchBreedsByName(breedName: String): List<CatBreed> {
        val breedModels = apiHelper.getBreedsByName(breedName)
        val favoriteBreeds = storageHelper.getAllFavoriteBreeds()
        return breedModels.map { breedModel ->
            breedModel.toCatBreed(
                isFavorite = favoriteBreeds.any { it.id == breedModel.id }
            )
        }
    }

    override suspend fun addFavoriteBreed(breed: CatBreed) {
        storageHelper.addFavoriteBreed(breed.toFavoriteBreedEntity())
    }

    private fun CatBreed.toFavoriteBreedEntity(): FavoriteBreedEntity = FavoriteBreedEntity(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
        lifeSpan = lifeSpan
    )

    override suspend fun removeFavoriteBreed(breed: CatBreed) {
        storageHelper.removeFavoriteBreed(breed.toFavoriteBreedEntity())
    }

    override fun getFavoriteBreeds(): Flow<List<CatBreed>> =
        storageHelper.getAllFavoriteBreedsObservable().map { favoriteBreedsList ->
            favoriteBreedsList.map { favoriteBreed ->
                favoriteBreed.toCatBreed()
            }
        }

    private fun FavoriteBreedEntity.toCatBreed(): CatBreed = CatBreed(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        imageUrl = imageUrl,
        lifeSpan = lifeSpan,
        isFavorite = true
    )

    override suspend fun getBreedById(breedsId: String): CatBreed =
        apiHelper.getBreedsById(breedsId)
            .toCatBreed(
                isFavorite = storageHelper.isFavoriteBreed(breedsId)
            )
}