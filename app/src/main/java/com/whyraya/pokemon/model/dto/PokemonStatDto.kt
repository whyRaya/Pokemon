package com.whyraya.pokemon.model.dto

import androidx.annotation.Keep

data class PokemonStatDto(
    val baseStat: Int = 0,
    val effort: Int = 0,
    val stat: PokemonDetailDto = PokemonDetailDto()
)
