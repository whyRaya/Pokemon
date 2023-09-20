package com.whyraya.pokemon.di

import com.whyraya.pokemon.domain.PokemonRepository
import com.whyraya.pokemon.domain.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideRepositoryImpl(repository: PokemonRepositoryImpl): PokemonRepository
}


