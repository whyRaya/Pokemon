package com.whyraya.pokemon.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.whyraya.pokemon.data.local.PokemonDao
import com.whyraya.pokemon.data.remote.PokemonRemoteDataSource
import com.whyraya.pokemon.mapper.PokemonDataMapper
import com.whyraya.pokemon.mapper.PokemonEntityMapper
import com.whyraya.pokemon.model.dto.PokemonCatchDto
import com.whyraya.pokemon.model.dto.PokemonDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.Random
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonDao,
    private val dataMapper: PokemonDataMapper,
    private val entityMapper: PokemonEntityMapper
) : PokemonRepository {
    override fun getPokemon(): Flow<PagingData<PokemonDto>> =
        remoteDataSource.getPokemon().map {
            it.map { response ->
                dataMapper.mapToPokemonDto(response)
            }
        }

    override fun getPokemonById(id: Int): Flow<PokemonDto> = flow {
        localDataSource.getPokemonDetail(id)?.let {
            emit(entityMapper.mapToPokemonDto(it))
        } ?: run {
            val response = remoteDataSource.getPokemonById(id)
            val dto = dataMapper.mapToPokemonDto(response)
            localDataSource.insertPokemonDetail(entityMapper.mapToPokemonDetailEntity(dto))
            emit(dto)
        }
    }.flowOn(Dispatchers.IO)

    override fun catchPokemon(pokemon: PokemonDto): Flow<PokemonCatchDto> = flow {
        val random = Random().nextDouble()
        delay(1500L) // more delay, for exiting moment ^^
        if (random > 0.5f) {
            val dto = pokemon.copy(owned = true)
             localDataSource.insertPokemonDetail(entityMapper.mapToPokemonDetailEntity(dto))
            emit(PokemonCatchDto(
                pokemonDto = dto,
                status = true
            ))
        } else {
            emit(PokemonCatchDto(
                pokemonDto = pokemon,
                status = false
            ))
        }
    }.flowOn(Dispatchers.IO)
}
