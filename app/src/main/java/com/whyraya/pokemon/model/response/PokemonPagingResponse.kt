package com.whyraya.pokemon.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonPagingResponse(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("results") val results: List<PokemonResponse>? = null
)
