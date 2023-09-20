package com.whyraya.pokemon.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.whyraya.pokemon.ui.navigation.DETAIL_ROUTE
import com.whyraya.pokemon.ui.navigation.POKEMON_ID
import com.whyraya.pokemon.ui.navigation.Screen

val LocalNavController = compositionLocalOf<NavHostController> { error("No nav controller") }
val LocalDarkTheme = compositionLocalOf { mutableStateOf(false) }

@Composable
fun MainContent() {
    val navController = LocalNavController.current
    NavHost(navController = navController, startDestination = Screen.HOME.route) {
        composable(Screen.HOME.route) { PokemonHomeScreen() }

        navigation(startDestination = Screen.DETAIL.route, route = DETAIL_ROUTE) {
            navArgument(POKEMON_ID) { type = NavType.IntType }

            fun NavBackStackEntry.pokemonId(): Int {
                return arguments?.getInt(POKEMON_ID)!!
            }

            val pokemonDetailViewModel: @Composable (movieId: Int) -> PokemonDetailViewModel =
                { hiltViewModel() }

            composable(route = Screen.DETAIL.route) {
                PokemonDetailScreen(pokemonDetailViewModel(it.pokemonId()))
            }
        }
    }
}
