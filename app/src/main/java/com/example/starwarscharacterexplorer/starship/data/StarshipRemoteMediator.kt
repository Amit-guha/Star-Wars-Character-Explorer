package com.example.starwarscharacterexplorer.starship.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarscharacterexplorer.network.CharacterApi
import com.example.starwarscharacterexplorer.starship.data.database.StarshipDatabase
import com.example.starwarscharacterexplorer.starship.model.Starship
import com.example.starwarscharacterexplorer.starship.model.StarshipRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class StarshipRemoteMediator
    (
    private val characterApi: CharacterApi,
    private val starshipDatabase: StarshipDatabase
) : RemoteMediator<Int, Starship>() {

    private val starshipDao = starshipDatabase.starShipDao()
    private val remoteDao = starshipDatabase.remoteStarShipDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Starship>
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
           val response = characterApi.getStarShip(currentPage)
            val endOfPaginationReached = response.next == null
           Log.d("end ->","end")

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1


            starshipDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    starshipDao.deleteAllStarShip()
                    remoteDao.deleteAllRemoteKeys()
                }

                starshipDao.addStarShip(response.results)
                val keys = response.results.map {character ->
                    StarshipRemoteKeys(
                        id = character.url,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteDao.addAllRemoteStarShip(keys)
            }
            MediatorResult.Success(endOfPaginationReached)

        }catch (exception : Exception){
            MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Starship>
    ): StarshipRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                remoteDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Starship>
    ): StarshipRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                remoteDao.getRemoteKeys(id = quote.url)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Starship>
    ): StarshipRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                remoteDao.getRemoteKeys(id = quote.url)
            }
    }


}