package com.fabiolourenco.corenetwork.api

import com.fabiolourenco.corenetwork.model.BreedModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("breeds")
    suspend fun getBreeds(): List<BreedModel>

    @GET("breeds/search")
    suspend fun getBreedsByName(@Query("q") breedName: String): List<BreedModel>

    @GET("breeds")
    suspend fun getBreedsPage(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<BreedModel>
}