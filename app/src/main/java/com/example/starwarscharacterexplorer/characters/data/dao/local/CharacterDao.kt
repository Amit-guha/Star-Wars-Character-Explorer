package com.example.starwarscharacterexplorer.characters.data.dao.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarscharacterexplorer.characters.model.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(characterList : List<Character>)

    @Query("SELECT * FROM Character_local")
    fun getCharacter() : PagingSource<Int,Character>

    @Query("DELETE FROM Character_local")
    suspend fun deleteAllCharacter()
}