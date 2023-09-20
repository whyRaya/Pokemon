package com.whyraya.pokemon.mapper

import com.whyraya.pokemon.model.dto.PokemonDetailDto
import com.whyraya.pokemon.model.dto.PokemonDto
import com.whyraya.pokemon.model.dto.PokemonMoveDto
import com.whyraya.pokemon.model.dto.PokemonStatDto
import com.whyraya.pokemon.model.dto.PokemonTypeDto
import com.whyraya.pokemon.model.response.PokemonResponse
import javax.inject.Inject

class PokemonDataMapper @Inject constructor() {

    fun mapToPokemonDto(response: PokemonResponse?) = response?.let {
        val id = it.id ?: getIdFromPokemonUrl(it.url)
        val formatId = formatId(id)
        PokemonDto(
            id = id,
            name = it.name.orEmpty().formatTitle(),
            url = it.url.orEmpty(),
            formatId = formatId,
            imageUrl = imageUrl(formatId),
            weight = it.weight.orZero(),
            height = it.height.orZero(),
            baseExperience = it.baseExperience.orZero(),
            stats = it.stats.mapToPokemonStatDto(),
            types = it.types.mapToPokemonTypeDto(),
            moves = it.moves.mapToPokemonMoveDto().filter { move -> move.levelLearnedAt > 0 }
                .sortedBy { move -> move.levelLearnedAt }
        )
    } ?: PokemonDto()

    private fun List<PokemonResponse.Stat>?.mapToPokemonStatDto() = orEmpty().map {
        PokemonStatDto(
            baseStat = it.baseStat.orZero(),
            effort = it.effort.orZero(),
            stat = it.stat.mapToPokemonDetailDto()
        )
    }

    private fun List<PokemonResponse.Type>?.mapToPokemonTypeDto() = orEmpty().map {
        PokemonTypeDto(
            slot = it.slot.orZero(),
            type = it.type.mapToPokemonDetailDto()
        )
    }

    private fun List<PokemonResponse.Move>?.mapToPokemonMoveDto() = orEmpty().map {
        PokemonMoveDto(
            move = it.move.mapToPokemonDetailDto(),
            levelLearnedAt = it.versionDetail.orEmpty().maxOfOrNull { level ->
                level.levelLearnedAt.orZero()
            }.orZero()
        )
    }

    private fun PokemonResponse.Detail?.mapToPokemonDetailDto() = this?.let {
        PokemonDetailDto(
            name = it.name.orEmpty().formatTitle(),
            url = it.url.orEmpty()
        )
    } ?: PokemonDetailDto()

    // "https://pokeapi.co/api/v2/pokemon/2/"
    private fun getIdFromPokemonUrl(url: String?): Int = url?.split(SEPARATOR)?.run {
        val range = 0..size.dec()
        for (i in range.reversed()) {
            if (this[i].isNotBlank() && this[i].toIntOrNull() != null) {
                return this[i].toInt()
            }
        }
        return -1
    } ?: 0

    private fun Int?.orZero(): Int = this ?: 0

    private fun String.formatTitle() = this.split("-").joinToString(" ") {
        it[0].uppercase() + it.substring(1)
    }

    private fun formatId(id: Int): String = id.toString().padStart(PAD_LENGTH, PAD_CHAR)

    private fun imageUrl(formatId: String): String = "$BASE_IMG_URL$formatId$BASE_IMG_EXT"

    companion object {
        private const val BASE_IMG_URL = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/"
        private const val BASE_IMG_EXT = ".png"
        private const val PAD_LENGTH = 3
        private const val PAD_CHAR = '0'
        private const val SEPARATOR = "/"
    }
}
