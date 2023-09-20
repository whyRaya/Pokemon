package com.whyraya.pokemon.mapper

import com.whyraya.pokemon.model.dto.PokemonDto
import com.whyraya.pokemon.model.response.PokemonResponse
import javax.inject.Inject

class PokemonDataMapper @Inject constructor() {

    fun mapToPokemonDto(response: PokemonResponse?) = response?.let {
        val id = getIdFromPokemonUrl(it.url)
        val formatId = formatId(id)
        PokemonDto(
            id = id,
            name = it.name.orEmpty(),
            url = it.url.orEmpty(),
            imageUrl = imageUrl(formatId),
        )
    } ?: PokemonDto()

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
