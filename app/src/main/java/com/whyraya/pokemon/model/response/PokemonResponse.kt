package com.whyraya.pokemon.model.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
)
