package com.whyraya.pokemon.ui.screen.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.whyraya.pokemon.model.dto.PokemonDto

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokeDexCard(
    modifier: Modifier = Modifier,
    pokemon: PokemonDto,
    isFavorite: Boolean = false,
    onPokemonSelected: (PokemonDto) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
    ) {
        Box(
            modifier
                .height(120.dp)
                .clickable { onPokemonSelected(pokemon) }
        ) {
            Column(
                Modifier.padding(top = 24.dp, start = 12.dp)
            ) {
                PokemonName(pokemon.name)
            }
            Text(
                text = pokemon.id.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 12.dp)
                    .graphicsLayer {
                        alpha = 0.5f
                    },
            )
//            PokeBall(
//                Modifier
//                    .align(Alignment.BottomEnd)
//                    .offset(x = 5.dp, y = 10.dp)
//                    .requiredSize(96.dp),
//                Color.White,
//                0.25f
//            )

            GlideImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.imageUrl,
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 6.dp, end = 6.dp)
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

@Composable
private fun PokemonName(name: String?) {
    Text(
        text = name ?: "",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PokeDexCardPreview() {
    val pokemon = PokemonDto(
        id = 1,
        name = "Bulbasaur",
        url = "https://pokeapi.co/api/v2/pokemon/1/",
        imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/001/png"
    )
    Surface {
        Column(
            Modifier.width(200.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            PokeDexCard(
                modifier = Modifier.fillMaxWidth(),
                pokemon = pokemon
            )
            PokeDexCard(
                modifier = Modifier.fillMaxWidth(),
                pokemon = pokemon
            )
            PokeDexCard(
                modifier = Modifier.fillMaxWidth(),
                pokemon = pokemon
            )
        }
    }
}
