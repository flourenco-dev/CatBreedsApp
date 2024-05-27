package com.fabiolourenco.catbreedsapp.core.storage.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteBreedEntity(
    @PrimaryKey val id: String
)