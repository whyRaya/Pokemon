package com.whyraya.pokemon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.whyraya.pokemon.model.entity.PokemonDetailEntity
import com.whyraya.pokemon.model.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemon(): List<PokemonEntity>?

    @Query("SELECT * FROM pokemonDetail WHERE owned = 1")
    suspend fun getAllPokemonOwned(): List<PokemonDetailEntity>?

    @Query("SELECT * FROM pokemonDetail where id = :id LIMIT 1")
    suspend fun getPokemonDetail(id: Int): PokemonDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonDetail(pokemon: PokemonDetailEntity)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemon()
}
