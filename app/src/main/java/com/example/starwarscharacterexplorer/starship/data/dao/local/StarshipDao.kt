package com.example.starwarscharacterexplorer.starship.data.dao.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarscharacterexplorer.starship.model.Starship

@Dao
interface StarshipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStarShip(starShipList : List<Starship>)

    @Query("SELECT * FROM Starship_local")
    fun getStarShip() : PagingSource<Int,Starship>

    @Query("DELETE FROM Starship_local")
    suspend fun deleteAllStarShip()
}