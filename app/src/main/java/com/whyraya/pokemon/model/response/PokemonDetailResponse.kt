package com.whyraya.pokemon.model.response

// image https://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png
// id = 1 => 001.png

// id, name, image

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val description: List<Description>,
    val detail: List<Detail>,
    val genderRate: Int?,
    val evolutionChain: EvolutionChain?,
    val species: List<Species>
) {
    data class Description(
        val flavorText: String,
    )

    data class Detail(
        val types: List<Type>,
        val stats: List<Stat>,
        val height: Int?,
        val weight: Int?,
        val moves: List<Move>,
    )

    data class Type(
        val type: Type1?,
    )

    data class Type1(
        val name: String,
    )

    data class Stat(
        val stat: Stat1?,
        val baseStat: Int,
    )

    data class Stat1(
        val id: Int,
        val name: String,
    )

    data class Move(
        val level: Int,
        val id: Int?,
    )

    data class EvolutionChain(
        val evolutions: List<Evolution>,
    )

    data class Evolution(
        val id: Int,
        val targetLevels: List<TargetLevel>,
    )

    data class TargetLevel(
        val level: Int?,
        val triggerType: Int?,
        val itemId: Int?,
    )

    data class Species(
        val genus: String,
    )

}
