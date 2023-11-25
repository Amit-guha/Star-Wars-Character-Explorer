package com.example.starwarscharacterexplorer.network

import com.example.starwarscharacterexplorer.characters.model.CharacterResponse
import com.example.starwarscharacterexplorer.planet.model.PlanetResponse
import com.example.starwarscharacterexplorer.starship.model.StarshipResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface CharacterApi {
    @GET("people/")
    suspend fun getCharacter(@Query("page") page : Int) : CharacterResponse

    @GET("starships/")
    suspend fun getStarShip(@Query("page") page : Int) : StarshipResponse

    @GET("planets/")
    suspend fun getPlanet(@Query("page") page : Int) : PlanetResponse
}