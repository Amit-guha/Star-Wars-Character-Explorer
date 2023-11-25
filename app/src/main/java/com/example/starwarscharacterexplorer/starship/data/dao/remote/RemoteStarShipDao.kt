package com.example.starwarscharacterexplorer.starship.data.dao.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarscharacterexplorer.starship.model.StarshipRemoteKeys

@Dao
interface RemoteStarShipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteStarShip(remoteKeys: List<StarshipRemoteKeys>)

    @Query("SELECT * FROM StarshipRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id : String) : StarshipRemoteKeys

    @Query("DELETE FROM StarshipRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}