package com.fabiolourenco.catbreedsapp.core.network

import com.fabiolourenco.catbreedsapp.core.network.api.CatApi
import com.fabiolourenco.catbreedsapp.core.network.model.BreedModel
import javax.inject.Inject

internal class ApiHelperImpl @Inject constructor(
    private val catApi: CatApi
) : ApiHelper {

    override suspend fun getBreeds(): List<BreedModel> = catApi.getBreeds()

    override suspend fun getBreedsByName(breedName: String): List<BreedModel> =
        catApi.getBreedsByName(breedName)
}