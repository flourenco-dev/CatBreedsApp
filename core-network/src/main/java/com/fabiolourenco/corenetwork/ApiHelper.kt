package com.fabiolourenco.corenetwork

import com.fabiolourenco.corenetwork.model.BreedModel

// API abstraction to hide implementation details from Repository
interface ApiHelper {
    suspend fun getBreeds(): List<BreedModel>
    suspend fun getBreedsByName(breedName: String): List<BreedModel>
    suspend fun getBreedsPage(page: Int, limit: Int): List<BreedModel>
}