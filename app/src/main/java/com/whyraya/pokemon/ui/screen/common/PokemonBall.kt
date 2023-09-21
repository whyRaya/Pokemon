package com.whyraya.pokemon.ui.screen.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyraya.pokemon.R
import com.whyraya.pokemon.ui.theme.PokemonTheme

@Composable
fun RotatingPokeBall(
    modifier: Modifier = Modifier,
    alpha: Float,
    duration: Int = 5000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing)
        )
    )
    PokeBall(
        modifier = modifier.graphicsLayer {
            rotationZ = angle
        },
        alpha = alpha
    )
}

@Composable
fun ScalingPokeBall(
    modifier: Modifier = Modifier,
    fromSize: Float,
    toSize: Float,
    duration: Int = 2000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val size by infiniteTransition.animateFloat(
        initialValue = fromSize, targetValue = toSize, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    PokeBall(
        modifier = modifier.graphicsLayer {
            scaleX = size
            scaleY = size
        }
    )
}

@Composable
fun PokeBall(
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
) {
    PokeBallBase(modifier = modifier, alpha = alpha)
}

@Composable
private fun PokeBallBase(
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    imageResId: Int = R.drawable.app_ic_pokemon_ball,
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = imageResId),
        contentDescription = "PokemonBall",
        alpha = alpha
    )
}

@Preview
@Composable
fun PreviewPokeBall() {
    PokemonTheme {
        Surface {
            Column {
                PokeBall()
                PokeBall(modifier = Modifier.size(56.dp))
            }
        }
    }
}
