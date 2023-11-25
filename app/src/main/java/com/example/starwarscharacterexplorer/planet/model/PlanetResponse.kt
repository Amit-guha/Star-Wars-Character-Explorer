package com.example.starwarscharacterexplorer.planet.model

data class PlanetResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Planet>
)
