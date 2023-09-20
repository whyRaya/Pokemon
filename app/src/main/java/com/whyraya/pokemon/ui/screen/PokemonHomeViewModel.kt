package com.whyraya.pokemon.ui.screen

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.whyraya.pokemon.domain.PokemonRepository
import com.whyraya.pokemon.model.dto.PokemonDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonHomeViewModel @Inject constructor(
    pokemonRepository: PokemonRepository
) : ViewModel() {

    val pokemon: Flow<PagingData<PokemonDto>> = pokemonRepository.getPokemon()
}
