package com.whyraya.pokemon.domain

import androidx.paging.PagingData
import com.whyraya.pokemon.model.dto.PokemonCatchDto
import com.whyraya.pokemon.model.dto.PokemonDto
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemon(): Flow<PagingData<PokemonDto>>

    fun getPokemonById(id: Int): Flow<PokemonDto>

    fun catchPokemon(pokemon: PokemonDto): Flow<PokemonCatchDto>
}
