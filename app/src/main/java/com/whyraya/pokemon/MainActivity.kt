package com.whyraya.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.whyraya.pokemon.ui.screen.LocalDarkTheme
import com.whyraya.pokemon.ui.screen.LocalNavController
import com.whyraya.pokemon.ui.screen.MainContent
import com.whyraya.pokemon.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val systemTheme = isSystemInDarkTheme()
            val isDarkTheme = remember { mutableStateOf(systemTheme) }
            val navController = rememberNavController()
            PokemonTheme(darkTheme = isDarkTheme.value) {
                CompositionLocalProvider(
                    LocalNavController provides navController,
                    LocalDarkTheme provides isDarkTheme,
                ) {
                    MainContent()
                }
            }
        }
    }
}
