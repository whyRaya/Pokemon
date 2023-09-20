package com.whyraya.pokemon.model.dto

data class PokemonTypeDto(
    val slot: Int = 0,
    val type: PokemonDetailDto = PokemonDetailDto()
)
