package com.example.starwarscharacterexplorer.planet.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.starwarscharacterexplorer.planet.data.dao.local.PlanetDao
import com.example.starwarscharacterexplorer.planet.data.dao.remote.RemotePlanetDao
import com.example.starwarscharacterexplorer.planet.model.Planet
import com.example.starwarscharacterexplorer.planet.model.PlanetRemoteKeys


@Database(entities = [Planet::class, PlanetRemoteKeys::class], version = 1)
abstract class PlanetDatabase : RoomDatabase() {

    abstract fun planetDao(): PlanetDao
    abstract fun remotePlanetDao(): RemotePlanetDao
}