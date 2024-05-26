package com.fabiolourenco.catbreedsapp.core.network.model

data class BreedModel(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val image: BreedImageModel?
)