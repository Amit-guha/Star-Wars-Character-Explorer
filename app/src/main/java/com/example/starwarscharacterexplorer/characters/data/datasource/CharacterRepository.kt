package com.example.starwarscharacterexplorer.characters.data.datasource

import com.example.starwarscharacterexplorer.characters.data.datasource.database.CharacterDatabase
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.starwarscharacterexplorer.network.CharacterApi
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRepository @Inject constructor(
    private val characterApi: CharacterApi,
    private val characterDatabase: CharacterDatabase
) {

    fun getCharacters() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100
        ),
        remoteMediator = CharacterRemoteMediator(characterApi, characterDatabase),
        pagingSourceFactory = { characterDatabase.characterDao().getCharacter()}
    ).liveData


}