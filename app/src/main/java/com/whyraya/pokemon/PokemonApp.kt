package com.whyraya.pokemon

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.navigation.DefaultNavigationViewModelDelegateFactory
import com.whyraya.pokemon.di.AppModuleProvider
import org.koin.core.context.GlobalContext.startKoin

class PokemonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //Mavericks.initialize(this)
        Mavericks.initialize(
            context = this,
            viewModelDelegateFactory = DefaultNavigationViewModelDelegateFactory()
        )

        startKoin{
            modules(AppModuleProvider.getInstance().modules)
        }
    }
}
