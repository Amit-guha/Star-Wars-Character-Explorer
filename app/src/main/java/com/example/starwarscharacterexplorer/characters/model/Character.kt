package com.example.starwarscharacterexplorer.characters.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "Character_local")
@Parcelize
data class Character(
    val birth_year: String,
    val created: String,
    val edited: String,
    val eye_color: String,
    //val films: List<String>,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    val skin_color: String,
   // val species: List<String>,
    //val starships: List<String>,
    @PrimaryKey(autoGenerate = false)
    val url: String
    //val vehicles: List<String>
):Parcelable