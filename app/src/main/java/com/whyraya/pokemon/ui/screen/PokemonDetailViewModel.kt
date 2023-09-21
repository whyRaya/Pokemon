package com.whyraya.pokemon.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyraya.pokemon.domain.PokemonRepository
import com.whyraya.pokemon.model.dto.PokemonCatchDto
import com.whyraya.pokemon.model.dto.PokemonDto
import com.whyraya.pokemon.ui.navigation.POKEMON_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val pokemonId = savedStateHandle.get<String>(POKEMON_ID)!!.toInt()

    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        getPokemonById()
    }

    fun getPokemonById() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(loading = true, error = null)
        try {
            pokemonRepository.getPokemonById(pokemonId).collect {
                _uiState.value = _uiState.value.copy(
                    pokemon = it,
                    loading = false,
                )
            }
        } catch (exception: Exception) {
            _uiState.value = _uiState.value.copy(error = exception, loading = false)
        }
    }

    fun catchPokemon(pokemon: PokemonDto) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(catchPokemonLoading = true)
        pokemonRepository.catchPokemon(pokemon).collect {
            _uiState.value = _uiState.value.copy(
                pokemon = it.pokemonDto,
                catchPokemonLoading = false,
                catchPokemonResult = it

            )
        }
    }

    data class PokemonDetailUiState(
        val pokemon: PokemonDto? = null,
        val loading: Boolean = false,
        val error: Throwable? = null,
        val catchPokemonLoading: Boolean = false,
        val catchPokemonResult: PokemonCatchDto? = null,
    )
}
