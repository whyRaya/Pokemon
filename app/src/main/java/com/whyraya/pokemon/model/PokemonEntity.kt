package com.whyraya.pokemon.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val uid: Int?,
//    @ColumnInfo(name = "types") val typeOfPokemon: List<String>?,
//    @ColumnInfo(name = "evolutions") val evolutionChain: List<EvolutionEntity>?,
//    @ColumnInfo(name = "moves") val movesList: List<PokemonMoveEntity>?,
//    @ColumnInfo(name = "name") val name: String?,
//    @ColumnInfo(name = "description") val description: String?,
//    @ColumnInfo(name = "category") val category: String?,
//    @ColumnInfo(name = "image") val image: Int?,
//    @ColumnInfo(name = "hp") val hp: Int?,
//    @ColumnInfo(name = "attack") val attack: Int?,
//    @ColumnInfo(name = "defense") val defense: Int?,
//    @ColumnInfo(name = "specialAttack") val specialAttack: Int?,
//    @ColumnInfo(name = "specialDefense") val specialDefense: Int?,
//    @ColumnInfo(name = "speed") val speed: Int?,
//    @ColumnInfo(defaultValue = "0.0") val height: Double,
//    @ColumnInfo(defaultValue = "0.0") val weight: Double,
//    @ColumnInfo(defaultValue = "-1") val genderRate: Int = -1,
)
