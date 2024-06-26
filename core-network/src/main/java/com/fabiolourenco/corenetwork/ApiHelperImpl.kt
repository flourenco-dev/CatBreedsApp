package com.fabiolourenco.corenetwork

import com.fabiolourenco.corenetwork.api.CatApi
import com.fabiolourenco.corenetwork.model.BreedModel
import javax.inject.Inject

internal class ApiHelperImpl @Inject constructor(
    private val catApi: CatApi
) : ApiHelper {

    override suspend fun getBreeds(): List<BreedModel> = catApi.getBreeds()

    override suspend fun getBreedsByName(breedName: String): List<BreedModel> =
        catApi.getBreedsByName(breedName)

    override suspend fun getBreedsPage(page: Int, limit: Int): List<BreedModel> =
        catApi.getBreedsPage(page, limit)
}