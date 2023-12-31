package com.whyraya.pokemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whyraya.pokemon.model.entity.PokemonDetailEntity
import com.whyraya.pokemon.model.entity.PokemonEntity

@Database(entities = [PokemonEntity::class, PokemonDetailEntity::class], version = 1)
abstract class PokemonDb : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
