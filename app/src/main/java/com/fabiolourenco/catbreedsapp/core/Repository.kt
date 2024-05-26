package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed

interface Repository {
    suspend fun getBreeds(): List<CatBreed>
    suspend fun searchBreedsByName(breedName: String): List<CatBreed>
}