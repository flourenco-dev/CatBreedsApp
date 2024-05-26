package com.fabiolourenco.catbreedsapp.core.network.api

import com.fabiolourenco.catbreedsapp.core.network.model.BreedModel
import retrofit2.http.GET

interface CatApi {
    @GET("breeds")
    suspend fun getBreeds(): List<BreedModel>
}