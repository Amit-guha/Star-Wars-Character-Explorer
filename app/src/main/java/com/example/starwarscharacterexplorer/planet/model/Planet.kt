package com.example.starwarscharacterexplorer.planet.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Planet_local")
@Parcelize
data class Planet(
    val name: String,
    val rotation_period: String,
    val orbital_period: String,
    val diameter: String,
    val climate: String,
    val gravity: String,
    val terrain: String,
    val surface_water: String,
    val population: String,
   // val residents: List<String>,
   // val films: List<String>,
    val created: String,
    val edited: String,
    @PrimaryKey(autoGenerate = false)
    val url: String
):Parcelable
