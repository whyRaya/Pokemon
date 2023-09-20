package com.whyraya.pokemon.model

data class PokemonEvolutionEntity(
    val id: Int,
    val targetLevel: Int = -1,
    val trigger: PokemonEvolutionTrigger = PokemonEvolutionTrigger.LevelUp,
    val itemId: Int = -1
)
enum class PokemonEvolutionTrigger(val value: Int) {
    LevelUp(1),
    UseItem(2),
    Trade(3);

    companion object {
        fun fromInt(value: Int) = PokemonEvolutionTrigger.values().first { it.value == value }
    }
}
