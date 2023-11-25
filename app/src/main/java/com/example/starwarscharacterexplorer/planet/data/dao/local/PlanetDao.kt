package com.example.starwarscharacterexplorer.planet.data.dao.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarscharacterexplorer.planet.model.Planet

@Dao
interface PlanetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlanet(starShipList : List<Planet>)

    @Query("SELECT * FROM Planet_local")
    fun getPlanet() : PagingSource<Int,Planet>

    @Query("DELETE FROM Planet_local")
    suspend fun deleteAllPlanet()
}