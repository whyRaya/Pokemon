package com.whyraya.pokemon.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemonDetail")
data class PokemonDetailEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "formatId") val formatId: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "weight") val weight: Int?,
    @ColumnInfo(name = "height") val height: Int?,
    @ColumnInfo(name = "owned") val owned: Boolean?,
    @ColumnInfo(name = "baseExperience") val baseExperience: Int?,
    @ColumnInfo(name = "types") val types: String?, // grass, fire, etc
    @ColumnInfo(name = "moves") val moves: String?  // overgrow, chlorophyll
)
