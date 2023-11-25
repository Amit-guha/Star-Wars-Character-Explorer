package com.example.starwarscharacterexplorer.starship.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StarshipRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)