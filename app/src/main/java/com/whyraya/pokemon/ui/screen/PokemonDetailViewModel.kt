package com.whyraya.pokemon.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyraya.pokemon.domain.PokemonRepository
import com.whyraya.pokemon.model.dto.PokemonDto
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        getPokemonById()
    }

    fun getPokemonById() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(loading = true, error = null)
        try {
            pokemonRepository.getPokemonById(1).collect {
                _uiState.value = _uiState.value.copy(
                    pokemon = it,
                    loading = false,
                )
            }
        } catch (exception: Exception) {
            _uiState.value = _uiState.value.copy(error = exception, loading = false)
        }
    }
    data class PokemonDetailUiState(
        val pokemon: PokemonDto? = null,
        val loading: Boolean = false,
        val error: Throwable? = null
    )
}
