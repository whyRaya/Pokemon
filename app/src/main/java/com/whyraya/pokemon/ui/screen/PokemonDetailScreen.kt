package com.whyraya.pokemon.ui.screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.whyraya.pokemon.ui.screen.common.RotatingPokeBall

val LocalVibrantColor =
    compositionLocalOf<Animatable<Color, AnimationVector4D>> { error("No vibrant color defined") }

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
                ConstraintLayout(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.surface)
                        .verticalScroll(rememberScrollState()).padding(bottom = 56.dp)
                ) {
                    val (header, pokemonBall, pokemonImage, pokemonMove) = createRefs()
                    val startGuideline = createGuidelineFromStart(16.dp)
                    val endGuideline = createGuidelineFromEnd(16.dp)
                    Header(pokemon = uiState.pokemon, modifier = Modifier.constrainAs(header) {})
                    RotatingPokeBall(
                        modifier = Modifier
                            .size(280.dp)
                            .constrainAs(pokemonBall) {
                                top.linkTo(header.bottom, 8.dp)
                                linkTo(startGuideline, endGuideline)
                            },
                        alpha = 0.5f
                    )
                    PokemonImage(
                        pokemon = uiState.pokemon,
                        modifier = Modifier
                            .size(180.dp)
                            .constrainAs(pokemonImage) {
                                centerHorizontallyTo(pokemonBall)
                                centerVerticallyTo(pokemonBall)
                                linkTo(startGuideline, endGuideline)
                            }
                    )

                    PokemonMove(
                        moves = uiState.pokemon.moves,
                        modifier = Modifier.constrainAs(pokemonMove) {
                            top.linkTo(pokemonBall.bottom, 8.dp)
                            linkTo(startGuideline, endGuideline)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Header(
    modifier: Modifier = Modifier,
    pokemon: PokemonDto
) {
    Column(
        modifier.padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.h4.copy(
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = stringResource(R.string.app_pokemon_id, pokemon.formatId),
                style = MaterialTheme.typography.h4.copy(
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier
                    .alignByBaseline()
                    .graphicsLayer { alpha = 0.75f }
            )
        }
        Spacer(Modifier.height(8.dp))
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
                    text = stringResource(R.string.app_pokemon_level, it.levelLearnedAt.toString(), it.move.name),
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
