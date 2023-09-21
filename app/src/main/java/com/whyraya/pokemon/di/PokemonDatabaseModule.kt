package com.whyraya.pokemon.di

import android.content.Context
import androidx.room.Room
import com.whyraya.pokemon.data.local.PokemonDao
import com.whyraya.pokemon.data.local.PokemonDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PokemonDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PokemonDb {
        return Room
            .databaseBuilder(
                appContext,
                PokemonDb::class.java,
                "pokemon.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovesDao(database: PokemonDb): PokemonDao {
        return database.pokemonDao()
    }
}
