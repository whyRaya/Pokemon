package com.whyraya.pokemon.ui.screen.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.whyraya.pokemon.R
import com.whyraya.pokemon.model.dto.PokemonDto
import com.whyraya.pokemon.ui.screen.common.PokeBall

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: PokemonDto,
    isFavorite: Boolean = false,
    onPokemonSelected: (PokemonDto) -> Unit = {}
) {
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = 8.dp,
        onClick = { onPokemonSelected(pokemon) },
    ) {
        Box(
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(
                Modifier.padding(top = 24.dp, start = 12.dp)
            ) {
                Text(
                    text = pokemon.name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .graphicsLayer {
                            alpha = 0.5f
                        },
                )
                Text(
                    text = stringResource(R.string.app_pokemon_id, pokemon.formatId),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .graphicsLayer {
                            alpha = 0.5f
                        },
                )
            }
            PokeBall(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .requiredSize(96.dp).offset(x = 24.dp),
                alpha = 0.25f
            )

            GlideImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.imageUrl,
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(bottom = 6.dp)
                    .size(80.dp)
            )
            if (isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 8.dp, end = 8.dp)
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PokemonCardPreview() {
    val pokemon = PokemonDto(
        id = 1,
        formatId = "001",
        name = "Bulbasaur",
        url = "https://pokeapi.co/api/v2/pokemon/1/",
        imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/001/png"
    )
    Surface {
        Column(
            Modifier.width(200.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            PokemonCard(
                modifier = Modifier.fillMaxWidth(),
                pokemon = pokemon
            )
            PokemonCard(
                modifier = Modifier.fillMaxWidth(),
                pokemon = pokemon
            )
        }
    }
}
