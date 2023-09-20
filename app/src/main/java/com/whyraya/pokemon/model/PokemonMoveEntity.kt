package com.whyraya.pokemon.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.whyraya.pokemon.R

@Entity
data class PokemonMoveEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val type: String,
    val pp: Int,
    val power: Int?,
    val accuracy: Int?
)
