package com.fabiolourenco.catbreedsapp.core

import com.fabiolourenco.catbreedsapp.common.uiModel.CatBreed

interface Repository {
    suspend fun getCatBreeds(): List<CatBreed>
}