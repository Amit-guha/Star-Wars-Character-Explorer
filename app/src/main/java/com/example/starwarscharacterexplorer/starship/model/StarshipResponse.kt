package com.example.starwarscharacterexplorer.starship.model

data class StarshipResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Starship>
)
