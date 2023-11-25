package com.example.starwarscharacterexplorer.planet.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.starwarscharacterexplorer.network.CharacterApi
import com.example.starwarscharacterexplorer.planet.data.database.PlanetDatabase
import javax.inject.Inject

@ExperimentalPagingApi
class PlanetRepository @Inject constructor(
    private val planetApi: CharacterApi,
    private val planetDatabase: PlanetDatabase
) {

    fun getPlanet() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100
        ),
        remoteMediator = PlanetRemoteMediator(planetApi, planetDatabase),
        pagingSourceFactory = { planetDatabase.planetDao().getPlanet()}
    ).liveData

}