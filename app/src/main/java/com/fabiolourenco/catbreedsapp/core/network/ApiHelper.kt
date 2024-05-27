package com.fabiolourenco.catbreedsapp.core.network

import com.fabiolourenco.catbreedsapp.core.network.model.BreedModel

// API abstraction to hide implementation details from Repository
interface ApiHelper {
    suspend fun getBreeds(): List<BreedModel>
    suspend fun getBreedsByName(breedName: String): List<BreedModel>
    suspend fun getBreedsById(breedId: String): BreedModel
}