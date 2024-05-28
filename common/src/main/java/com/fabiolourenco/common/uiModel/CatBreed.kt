package com.fabiolourenco.common.uiModel

data class CatBreed(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val imageUrl: String?,
    val lifeSpan: Int?,
    val isFavorite: Boolean = false
)