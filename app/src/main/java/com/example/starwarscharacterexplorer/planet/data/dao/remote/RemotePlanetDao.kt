package com.example.starwarscharacterexplorer.planet.data.dao.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarscharacterexplorer.planet.model.PlanetRemoteKeys

@Dao
interface RemotePlanetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemotePlanet(remoteKeys: List<PlanetRemoteKeys>)

    @Query("SELECT * FROM PlanetRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id : String) : PlanetRemoteKeys

    @Query("DELETE FROM PlanetRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}