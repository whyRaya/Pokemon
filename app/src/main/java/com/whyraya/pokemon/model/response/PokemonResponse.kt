package com.whyraya.pokemon.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("stats") val stats: List<Stat>? = null, // hp, atk, def, etc
    @SerializedName("types") val types: List<Type>? = null, // grass, fire, etc
    @SerializedName("moves") val moves: List<Move>? = null, // overgrow, chlorophyll
    @SerializedName("base_experience") val baseExperience: Int? = null,
    @SerializedName("weight") val weight: Int? = null,
    @SerializedName("height") val height: Int? = null
) {
    @Keep
    data class Stat(
        @SerializedName("base_stat") val baseStat: Int? = null,
        @SerializedName("effort") val effort: Int? = null,
        @SerializedName("stat") val stat: Detail? = null
    )

    @Keep
    data class Type(
        @SerializedName("slot") val slot: Int? = null,
        @SerializedName("type") val type: Detail? = null
    )

    @Keep
    data class Move(
        @SerializedName("move") val move: Detail? = null,
        @SerializedName("version_group_details") val versionDetail: List<Level>? = null
    )

    @Keep
    data class Level(
        @SerializedName("level_learned_at") val levelLearnedAt: Int? = null
    )

    @Keep
    data class Detail(
        @SerializedName("name") val name: String? = null,
        @SerializedName("url") val url: String? = null
    )

}
