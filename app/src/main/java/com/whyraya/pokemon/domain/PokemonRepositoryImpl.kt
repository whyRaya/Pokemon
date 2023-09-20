package com.whyraya.pokemon.domain

import com.whyraya.pokemon.data.remote.PokemonRemoteDataSource

class PokemonRepositoryImpl(
    val remoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {
}
