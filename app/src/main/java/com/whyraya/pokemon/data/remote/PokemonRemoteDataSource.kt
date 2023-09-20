package com.whyraya.pokemon.data.remote

import androidx.paging.PagingData
import com.whyraya.pokemon.model.response.PokemonResponse
import kotlinx.coroutines.flow.Flow

class PokemonRemoteDataSource(val apiServices: PokemonApiServices) {

    fun getPokemon(): Flow<PagingData<PokemonResponse>> = withPager { page ->
        val offset = page * LIMIT - LIMIT
        apiServices.getPokemon(offset, LIMIT).body()?.results.orEmpty()
    }.flow

    companion object {
        private const val LIMIT = 25
    }
}
