package com.whyraya.pokemon.model.dto

data class PokemonMoveDto(
    val levelLearnedAt: Int = 0,
    val move: PokemonDetailDto = PokemonDetailDto()
)
