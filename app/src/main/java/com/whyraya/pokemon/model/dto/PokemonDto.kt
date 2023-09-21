package com.whyraya.pokemon.model.dto

data class PokemonDto(
    val id: Int = 0,
    val name: String = "",
    val url: String = "",
    val formatId: String = "",
    val imageUrl: String = "",
    val weight: Int = 0,
    val height: Int = 0,
    val baseExperience: Int = 0,
    val owned: Boolean = false,
    val stats: List<PokemonStatDto> = emptyList(), // hp, atk, def, etc
    val types: List<PokemonTypeDto> = emptyList(), // grass, fire, etc
    val moves: List<PokemonMoveDto> = emptyList()  // overgrow, chlorophyll
)
