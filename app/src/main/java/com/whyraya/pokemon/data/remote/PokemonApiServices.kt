package com.whyraya.pokemon.data.remote

import com.whyraya.pokemon.model.response.PokemonPagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiServices {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 0
    ): Response<PokemonPagingResponse>
}
