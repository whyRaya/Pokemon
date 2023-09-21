package com.whyraya.pokemon.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.whyraya.pokemon.model.dto.PokemonDto
import com.whyraya.pokemon.model.dto.PokemonMoveDto
import com.whyraya.pokemon.model.dto.PokemonTypeDto
import com.whyraya.pokemon.model.entity.PokemonDetailEntity
import javax.inject.Inject

class PokemonEntityMapper @Inject constructor(private val gson: Gson) {

    fun mapToPokemonDto(entity: PokemonDetailEntity?) = entity?.let {
        PokemonDto(
            id = it.id,
            name = it.name.orEmpty(),
            formatId = it.formatId.orEmpty(),
            imageUrl = it.imageUrl.orEmpty(),
            weight = it.weight.orZero(),
            height = it.height.orZero(),
            owned = it.owned.orFalse(),
            baseExperience = it.baseExperience.orZero(),
            types = gson.fromJson<List<PokemonTypeDto>>(it.types).orEmpty(),
            moves = gson.fromJson<List<PokemonMoveDto>>(it.moves).orEmpty()
        )
    } ?: PokemonDto()

    private inline fun <reified T> Gson.fromJson(json: String?) = json?.let {
        fromJson<T>(json, object: TypeToken<T>() {}.type)
    }

    fun mapToPokemonDetailEntity(dto: PokemonDto): PokemonDetailEntity {
        val move = gson.toJson(dto.moves)
        return PokemonDetailEntity(
            id = dto.id,
            name = dto.name,
            formatId = dto.formatId,
            imageUrl = dto.imageUrl,
            weight = dto.weight,
            height = dto.height,
            owned = dto.owned,
            baseExperience = dto.baseExperience,
            types = gson.toJson(dto.types),
            moves = move
        )
    }

    private fun Int?.orZero(): Int = this ?: 0

    private fun Boolean?.orFalse(): Boolean = this ?: false

}
