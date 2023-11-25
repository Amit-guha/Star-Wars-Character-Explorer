package com.example.starwarscharacterexplorer.starship.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.starwarscharacterexplorer.network.CharacterApi
import com.example.starwarscharacterexplorer.starship.data.database.StarshipDatabase
import javax.inject.Inject

@ExperimentalPagingApi
class StarShipRepository @Inject constructor(
    private val starshipApi: CharacterApi,
    private val starshipDatabase: StarshipDatabase
) {

    fun getStarShips() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100
        ),
        remoteMediator = StarshipRemoteMediator(starshipApi, starshipDatabase),
        pagingSourceFactory = { starshipDatabase.starShipDao().getStarShip()}
    ).liveData

}