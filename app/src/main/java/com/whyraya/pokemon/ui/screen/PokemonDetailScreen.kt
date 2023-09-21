package com.whyraya.pokemon.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.whyraya.pokemon.R
import com.whyraya.pokemon.model.dto.PokemonDto
import com.whyraya.pokemon.model.dto.PokemonMoveDto
import com.whyraya.pokemon.ui.screen.common.ErrorColumn
import com.whyraya.pokemon.ui.screen.common.LoadingColumn
import com.whyraya.pokemon.ui.screen.common.PokemonAppBar
import com.whyraya.pokemon.ui.screen.common.RotatingPokeBall
import com.whyraya.pokemon.ui.screen.common.ScalingPokeBall

val LocalVibrantColor =
    compositionLocalOf<Animatable<Color, AnimationVector4D>> { error("No vibrant color defined") }

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PokemonDetailScreen(viewModel: PokemonDetailViewModel) {
    val uiState = viewModel.uiState.collectAsState().value
    when {
        uiState.loading -> LoadingColumn(stringResource(id = R.string.app_loading))
        uiState.error != null -> ErrorColumn(uiState.error.message.orEmpty()) {
            viewModel.getPokemonById()
        }

        uiState.pokemon != null -> {
            val defaultTextColor = MaterialTheme.colors.onBackground
            val vibrantColor = remember { Animatable(defaultTextColor) }
            CompositionLocalProvider(
                LocalVibrantColor provides vibrantColor
            ) {
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
                                PokemonAppBar(uiState.pokemon.name)
                            }
                        }
                    },
                    content = {
                        Box {
                            PokemonDetailContent(uiState.pokemon) {
                                viewModel.catchPokemon()
                            }
                            AnimatedVisibility(
                                visible = uiState.catchPokemonLoading,
                                enter = fadeIn(
                                    initialAlpha = 0.6f
                                ),
                                exit = fadeOut(
                                    animationSpec = tween(durationMillis = 250)
                                )
                            ) {
                                LoadingColumn(
                                    title = "",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color(0xA6282829))
                                )
                            }
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun PokemonDetailContent(
    pokemon: PokemonDto,
    catchPokemon: () -> Unit = {}
) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 56.dp)
    ) {
        val (header, pokemonBall, pokemonImage, pokemonMove, catchButton, owned) = createRefs()
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)
        Header(pokemon = pokemon, modifier = Modifier.constrainAs(header) {})
        RotatingPokeBall(
            modifier = Modifier
                .size(280.dp)
                .constrainAs(pokemonBall) {
                    top.linkTo(header.bottom)
                    linkTo(startGuideline, endGuideline)
                },
            alpha = 0.5f
        )
        PokemonImage(
            pokemon = pokemon,
            modifier = Modifier
                .size(180.dp)
                .constrainAs(pokemonImage) {
                    centerHorizontallyTo(pokemonBall)
                    centerVerticallyTo(pokemonBall)
                    linkTo(startGuideline, endGuideline)
                }
        )
        if (pokemon.owned) {
            Text(
                text = stringResource(R.string.app_pokemon_owned),
                style = MaterialTheme.typography.h5.copy(letterSpacing = 2.sp, color = Color.White),
                modifier = Modifier
                    .background(color = Color(0xFF3F51B5), RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .constrainAs(owned) {
                        top.linkTo(pokemonImage.bottom)
                        linkTo(startGuideline, endGuideline)
                    },
            )
        } else {
            ScalingPokeBall(
                modifier = Modifier
                    .size(56.dp)
                    .clickable {
                        catchPokemon.invoke()
                    }
                    .constrainAs(catchButton) {
                        top.linkTo(pokemonImage.bottom)
                        linkTo(startGuideline, endGuideline)
                    },
                fromSize = 1.0f,
                toSize = 1.1f,
                duration = 1200
            )
        }
        PokemonMove(
            moves = pokemon.moves,
            modifier = Modifier.constrainAs(pokemonMove) {
                top.linkTo(pokemonBall.bottom, 8.dp)
                linkTo(startGuideline, endGuideline)
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Header(
    modifier: Modifier = Modifier,
    pokemon: PokemonDto
) {
    Column(
        modifier.padding(top = 21.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
    ) {
        FlowRow(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pokemon.types.map {
                Text(
                    text = it.type.name,
                    style = MaterialTheme.typography.subtitle1.copy(letterSpacing = 2.sp),
                    modifier = Modifier
                        .border(1.25.dp, LocalVibrantColor.current.value, RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonImage(
    pokemon: PokemonDto,
    modifier: Modifier
) {
    GlideImage(
        model = pokemon.imageUrl,
        contentDescription = pokemon.imageUrl,
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PokemonMove(moves: List<PokemonMoveDto>, modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            text = stringResource(R.string.app_pokemon_moves),
            color = LocalVibrantColor.current.value,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            ),
        )
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            moves.map {
                Text(
                    text = stringResource(
                        R.string.app_pokemon_level,
                        it.levelLearnedAt.toString(),
                        it.move.name
                    ),
                    style = MaterialTheme.typography.subtitle1.copy(letterSpacing = 2.sp),
                    modifier = Modifier
                        .border(1.25.dp, LocalVibrantColor.current.value, RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}
