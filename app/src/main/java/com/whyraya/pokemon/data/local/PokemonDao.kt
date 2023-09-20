package com.whyraya.pokemon.data.local

import androidx.room.Dao
import androidx.room.Query
import com.whyraya.pokemon.model.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAll(): List<PokemonEntity>
}
