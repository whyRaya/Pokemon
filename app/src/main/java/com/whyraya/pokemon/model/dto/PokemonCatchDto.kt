package com.whyraya.pokemon.model.dto

data class PokemonCatchDto(
    val pokemonDto: PokemonDto = PokemonDto(),
    val status: Boolean = false
)
