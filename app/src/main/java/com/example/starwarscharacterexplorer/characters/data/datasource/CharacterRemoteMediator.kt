package com.example.starwarscharacterexplorer.characters.data.datasource

import com.example.starwarscharacterexplorer.characters.data.datasource.database.CharacterDatabase
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarscharacterexplorer.characters.model.Character
import com.example.starwarscharacterexplorer.characters.model.CharacterRemoteKeys
import com.example.starwarscharacterexplorer.network.CharacterApi

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator
    (
    private val characterApi: CharacterApi,
    private val characterDatabase: CharacterDatabase
) : RemoteMediator<Int, Character>() {

    private val characterDao = characterDatabase.characterDao()
    private val remoteDao = characterDatabase.remoteCharacterDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
       return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1

                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

           Log.d("start ->","Start")
           val response = characterApi.getCharacter(currentPage)
            val endOfPaginationReached = response.next == null
           Log.d("end ->","end")

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1


            characterDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterDao.deleteAllCharacter()
                    remoteDao.deleteAllRemoteKeys()
                }
                characterDao.addCharacter(response.results)
                val keys = response.results.map {character ->
                    CharacterRemoteKeys(
                        id = character.url,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteDao.addAllRemoteCharacter(keys)
            }
            MediatorResult.Success(endOfPaginationReached)

        }catch (exception : Exception){
            MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                remoteDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                remoteDao.getRemoteKeys(id = quote.url)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                remoteDao.getRemoteKeys(id = quote.url)
            }
    }



}