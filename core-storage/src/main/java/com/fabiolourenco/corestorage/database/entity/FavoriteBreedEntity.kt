package com.fabiolourenco.corestorage.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteBreedEntity(
    @PrimaryKey val id: String
)