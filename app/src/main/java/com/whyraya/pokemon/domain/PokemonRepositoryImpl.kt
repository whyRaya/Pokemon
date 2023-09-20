package com.whyraya.pokemon.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.whyraya.pokemon.data.remote.PokemonRemoteDataSource
import com.whyraya.pokemon.mapper.PokemonDataMapper
import com.whyraya.pokemon.model.dto.PokemonDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val dataMapper: PokemonDataMapper
) : PokemonRepository {
    override fun getPokemon(): Flow<PagingData<PokemonDto>> =
        remoteDataSource.getPokemon().map {
            it.map { response ->
                dataMapper.mapToPokemonDto(response)
            }
        }

    override fun getPokemonById(id: Int): Flow<PokemonDto> = flow {
        val response = remoteDataSource.getPokemonById(id)
        emit(dataMapper.mapToPokemonDto(response))
    }.flowOn(Dispatchers.IO)
}
