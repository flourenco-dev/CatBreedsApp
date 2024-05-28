package com.fabiolourenco.corenetwork.model

import com.google.gson.annotations.SerializedName

data class BreedModel(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val image: BreedImageModel?,
    @SerializedName("life_span") val lifeSpan: String?
)