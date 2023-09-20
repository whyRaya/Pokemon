package com.whyraya.pokemon.data.remote

import androidx.paging.PagingData
import com.whyraya.pokemon.model.response.PokemonResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(private val apiServices: PokemonApiServices) {

    fun getPokemon(): Flow<PagingData<PokemonResponse>> = withPager(pageSize = LIMIT) { page ->
        apiServices.getPokemon(offset = page * LIMIT, limit = LIMIT).body()?.results.orEmpty()
    }.flow

    companion object {
        private const val LIMIT = 20
    }
}
