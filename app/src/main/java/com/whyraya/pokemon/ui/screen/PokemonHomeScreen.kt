package com.whyraya.pokemon.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.whyraya.pokemon.ui.navigation.Screen
import com.whyraya.pokemon.ui.screen.common.ErrorColumn
import com.whyraya.pokemon.ui.screen.common.ErrorRow
import com.whyraya.pokemon.ui.screen.common.LoadingColumn
import com.whyraya.pokemon.ui.screen.common.LoadingRow
import com.whyraya.pokemon.ui.screen.common.PokemonAppBar
import com.whyraya.pokemon.ui.screen.common.toDp
import com.whyraya.pokemon.ui.screen.home.PokemonCard

private const val COLUMN_COUNT = 2
private val GRID_SPACING = 8.dp

private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(COLUMN_COUNT) }

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PokemonHomeScreen() {
    val viewModel: PokemonHomeViewModel = hiltViewModel()
    val pokemon = viewModel.pokemon.collectAsLazyPagingItems()
    val state = rememberLazyGridState()

    AnimatedContent(
        pokemon.loadState.refresh,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(500)
            ) togetherWith fadeOut(animationSpec = tween(500))
        },
        label = "Animated Content"
    ) {
        when (it) {
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
                    topBar = {
                        Surface(modifier = Modifier.fillMaxWidth(), elevation = 16.dp) {
                            Column(
                                Modifier
                                    .background(MaterialTheme.colors.surface)
                                    .padding(bottom = 2.dp),
                            ) {
                                PokemonAppBar(stringResource(id = R.string.app_name))
                            }
                        }
                    },
                    content = {
                        LazyMoviesGrid(state, pokemon)
                    }
                )
            }
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
private fun LazyMoviesGrid(state: LazyGridState, pokemonPagingItems: LazyPagingItems<PokemonDto>) {
    val loaded = remember { MutableTransitionState(false) }
    val navController = LocalNavController.current
    AnimatedContent(
        targetState = loaded,
        transitionSpec = {
            slideInHorizontally(
                animationSpec = tween(1000),
                initialOffsetX = { fullWidth -> fullWidth }
            ) togetherWith slideOutHorizontally(
                animationSpec = tween(1000),
                targetOffsetX = { fullWidth -> -fullWidth }
            )
        }
    ) {
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
            horizontalArrangement = Arrangement.spacedBy(
                GRID_SPACING,
                Alignment.CenterHorizontally
            ),
            state = state,
            content = {
                if (pokemonPagingItems.itemCount == 0 && pokemonPagingItems.loadState.refresh !is LoadState.Loading) {
                    item(span = span) {
                        ErrorRow("Error")
                    }
                }
                items(pokemonPagingItems.itemCount) { index ->
                    val pokemon = pokemonPagingItems.peek(index) ?: return@items
                    PokemonCard(
                        pokemon = pokemon,
                        modifier = Modifier.padding(vertical = GRID_SPACING)
                    ) {
                        navController.navigate(Screen.DETAIL.createPath(pokemon.id))
                    }
                }

                renderLoading(pokemonPagingItems.loadState)
                renderError(pokemonPagingItems.loadState)
            },
        )
    }
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
