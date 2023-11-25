package com.example.starwarscharacterexplorer.characters.model


data class CharacterResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Character>
)