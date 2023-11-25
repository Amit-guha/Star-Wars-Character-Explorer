package com.example.starwarscharacterexplorer.characters.data.datasource.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.starwarscharacterexplorer.characters.model.Character
import com.example.starwarscharacterexplorer.characters.data.dao.local.CharacterDao
import com.example.starwarscharacterexplorer.characters.data.dao.remote.RemoteCharacterDao
import com.example.starwarscharacterexplorer.characters.model.CharacterRemoteKeys


@Database(entities = [Character::class, CharacterRemoteKeys::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteCharacterDao(): RemoteCharacterDao
}