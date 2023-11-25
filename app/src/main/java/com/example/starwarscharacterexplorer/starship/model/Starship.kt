package com.example.starwarscharacterexplorer.starship.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Starship_local")
@Parcelize
data class Starship(
    val name: String,
    val model: String,
    val manufacturer: String,
    val cost_in_credits: String,
    val length: String,
    val max_atmosphering_speed: String,
    val crew: String,
    val passengers: String,
    val cargo_capacity: String,
    val consumables: String,
    val hyperdrive_rating: String,
    val MGLT: String,
    val starship_class: String,
   // val pilots: List<String>,
   // val films: List<String>,
    val created: String,
    val edited: String,
    @PrimaryKey(autoGenerate = false)
    val url: String
):Parcelable