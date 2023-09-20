package com.whyraya.pokemon.ui.navigation

const val HOME_ROUTE = "genres"

const val DETAIL_ROUTE = "detail"
const val POKEMON_ID = "POKEMON_ID"

enum class Screen(val route: String) {
    HOME(HOME_ROUTE),
    DETAIL("$DETAIL_ROUTE/{$POKEMON_ID}");

    fun createPath(vararg args: Any): String {
        var route = route
        require(args.size == route.argumentCount) {
            "Provided ${args.count()} parameters, was expected ${route.argumentCount} parameters!"
        }
        route.arguments().forEachIndexed { index, matchResult ->
            route = route.replace(matchResult.value, args[index].toString())
        }
        return route
    }
}
