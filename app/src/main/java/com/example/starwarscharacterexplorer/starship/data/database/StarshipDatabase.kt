package com.example.starwarscharacterexplorer.starship.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.starwarscharacterexplorer.starship.data.dao.local.StarshipDao
import com.example.starwarscharacterexplorer.starship.data.dao.remote.RemoteStarShipDao
import com.example.starwarscharacterexplorer.starship.model.Starship
import com.example.starwarscharacterexplorer.starship.model.StarshipRemoteKeys


@Database(entities = [Starship::class, StarshipRemoteKeys::class], version = 1)
abstract class StarshipDatabase : RoomDatabase() {

    abstract fun starShipDao(): StarshipDao
    abstract fun remoteStarShipDao(): RemoteStarShipDao
}