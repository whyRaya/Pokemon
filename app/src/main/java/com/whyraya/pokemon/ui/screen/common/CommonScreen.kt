package com.whyraya.pokemon.ui.screen.common


import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyraya.pokemon.R
import com.whyraya.pokemon.ui.screen.LocalDarkTheme
import com.whyraya.pokemon.ui.screen.LocalNavController
import com.whyraya.pokemon.ui.screen.LocalVibrantColor


@Composable
fun LoadingColumn(title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RotatingPokeBall(
            modifier = Modifier
                .size(56.dp),
            alpha = 0.6f,
            duration = 1000
        )
        Text(title, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun LoadingRow(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RotatingPokeBall(
            modifier = Modifier
                .size(56.dp),
            alpha = 0.6f,
            duration = 1000
        )
        Text(title, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun ErrorColumn(message: String, modifier: Modifier = Modifier, reload: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(message)
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(top = 16.dp),
        )
        Button(
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
            onClick = { reload.invoke() }) {
            Text("Reload")
        }
    }
}

@Composable
fun ErrorRow(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.ErrorOutline,
            contentDescription = "",
            modifier = Modifier.size(40.dp),
        )
        Text(title)
    }
}

@Composable
fun Int.toDp(): Float {
    val density = LocalDensity.current.density
    return remember(this) { this / density }
}

@Composable
fun Int.dpToPx(): Float {
    val density = LocalDensity.current.density
    return remember(this) { this * density }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PokemonCardPreview() {
    Surface {
        Column(
            Modifier.width(200.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            LoadingColumn(title = stringResource(id = R.string.app_loading))
            LoadingRow(title = stringResource(id = R.string.app_loading))
        }
    }
}

@Composable
fun PokemonAppBar(title: String) {
    val navController = LocalNavController.current
    val colors = androidx.compose.material.MaterialTheme.colors
    val isDarkTheme = LocalDarkTheme.current
    val iconTint =
        animateColorAsState(
            if (isDarkTheme.value) colors.onSurface else colors.primary,
            label = "appIconTint"
        ).value
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "", tint = iconTint)
        }
        Text(
            text = title,
            style = androidx.compose.material.MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            ),
        )
        Spacer(modifier = Modifier.size(32.dp))
//        val icon = if (isDarkTheme.value) Icons.Default.NightsStay else Icons.Default.WbSunny
//        IconButton(onClick = { isDarkTheme.value = !isDarkTheme.value }) {
//            Icon(icon, contentDescription = null, tint = iconTint)
//        }
    }
}
