package com.example.starwarscharacterexplorer.planet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlanetRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)