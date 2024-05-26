package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed
import com.fabiolourenco.catbreedsapp.core.network.ApiHelper
import com.fabiolourenco.catbreedsapp.core.network.model.BreedModel
import javax.inject.Inject

internal class RepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper
) : Repository {

    override suspend fun getBreeds(): List<CatBreed> = apiHelper.getBreeds().map {
        it.toCatBreed()
    }

    private fun BreedModel.toCatBreed(): CatBreed = CatBreed(
        name = name,
        origin = origin,
        imageUrl = image?.url
    )

    override suspend fun searchBreedsByName(breedName: String): List<CatBreed> =
        apiHelper.getBreedsByName(breedName).map {
            it.toCatBreed()
        }
}