package com.fabiolourenco.corestorage.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BreedEntity(
    @PrimaryKey val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val imageUrl: String?,
    val lifeSpan: Int?,
    val isFavorite: Boolean
)