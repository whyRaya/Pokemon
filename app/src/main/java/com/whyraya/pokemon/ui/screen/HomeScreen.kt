package com.whyraya.pokemon.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.whyraya.pokemon.R
import com.whyraya.pokemon.model.dto.PokemonDto
import com.whyraya.pokemon.ui.screen.home.PokeDexCard

private const val COLUMN_COUNT = 2
private val GRID_SPACING = 8.dp

private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(COLUMN_COUNT) }

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val pokemon = viewModel.pokemon.collectAsLazyPagingItems()
    val state = rememberLazyGridState()

    when (pokemon.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingColumn(stringResource(id = R.string.app_loading))
        }
        is LoadState.Error -> {
            val error = pokemon.loadState.refresh as LoadState.Error
            ErrorColumn(error.error.message.orEmpty()) {
                pokemon.refresh()
            }
        }
        else -> {
            Scaffold(
                modifier = Modifier
                    .statusBarsPadding()
                    .background(MaterialTheme.colors.surface),
                content = {
                    LazyMoviesGrid(state, pokemon)
                }
            )
        }
    }
}

@Composable
private fun LazyMoviesGrid(state: LazyGridState, pokemonPagingItems: LazyPagingItems<PokemonDto>) {
    Log.e("POKEMON", "state: $state")
    LazyVerticalGrid(
        columns = GridCells.Fixed(COLUMN_COUNT),
        contentPadding = PaddingValues(
            start = GRID_SPACING,
            top = GRID_SPACING,
            end = GRID_SPACING,
            bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current).toDp().dp.plus(
                GRID_SPACING
            ),
        ),
        horizontalArrangement = Arrangement.spacedBy(GRID_SPACING, Alignment.CenterHorizontally),
        state = state,
        content = {
            if (pokemonPagingItems.itemCount == 0 && pokemonPagingItems.loadState.refresh !is LoadState.Loading) {
                item(span = span) {
                    ErrorRow("Error")
                }
            }
            items(pokemonPagingItems.itemCount) { index ->
                val pokemon = pokemonPagingItems.peek(index) ?: return@items
                PokeDexCard(pokemon = pokemon)
            }
            renderLoading(pokemonPagingItems.loadState)
            renderError(pokemonPagingItems.loadState)
        },
    )
}

private fun LazyGridScope.renderLoading(loadState: CombinedLoadStates) {
    if (loadState.append !is LoadState.Loading) return

    item(span = span) {
        val title = stringResource(R.string.app_loading)
        LoadingRow(title = title, modifier = Modifier.padding(vertical = GRID_SPACING))
    }
}

private fun LazyGridScope.renderError(loadState: CombinedLoadStates) {
    val message = (loadState.append as? LoadState.Error)?.error?.message ?: return

    item(span = span) {
        ErrorRow(title = message, modifier = Modifier.padding(vertical = GRID_SPACING))
    }
}
