package com.example.starwarscharacterexplorer.characters.data.dao.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarscharacterexplorer.characters.model.CharacterRemoteKeys

@Dao
interface RemoteCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteCharacter(remoteKeys: List<CharacterRemoteKeys>)

    @Query("SELECT * FROM CharacterRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id : String) : CharacterRemoteKeys

    @Query("DELETE FROM CharacterRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}