package com.whyraya.pokemon.di

import androidx.room.Room
import com.google.gson.Gson
import com.whyraya.pokemon.data.local.PokemonLocalDataSource
import com.whyraya.pokemon.data.remote.PokemonApiServices
import com.whyraya.pokemon.data.remote.PokemonRemoteDataSource
import com.whyraya.pokemon.data.local.PokemonDb
import com.whyraya.pokemon.domain.PokemonRepository
import com.whyraya.pokemon.domain.PokemonRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppModuleProvider {

    val modules: List<Module> get() = mutableListOf<Module>().apply {
        networkModule
        repositoryModule
    }.toList()

    private val networkModule = module {
        single { provideOkHttpClient() }
        single { provideRetrofit(okHttpClient = get()) }
        single { Gson() }
        single {
            provideApiService(retrofit = get())
        }
    }

    private val repositoryModule = module {
        single {
            PokemonRemoteDataSource(apiServices = get())
        }
//        single {
//            Room.databaseBuilder(
//                context = get(),
//                PokemonDb::class.java, "database-pokemon"
//            ).build()
//        }
//        single {
//            val database = get<PokemonDb>()
//            database.pokemonDao()
//        }
        single {
            PokemonLocalDataSource(db = get())
        }
        factory {
            PokemonRepositoryImpl(
                remoteDataSource = get()
            )
        } bind PokemonRepository::class
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    private fun provideApiService(retrofit: Retrofit): PokemonApiServices {
        return retrofit.create(PokemonApiServices::class.java)
    }


    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
        private const val REQUEST_TIMEOUT = 10L
        private var INSTANCE: AppModuleProvider? = null

        fun getInstance(): AppModuleProvider {
            return INSTANCE ?: synchronized(AppModuleProvider::class) {
                return@synchronized AppModuleProvider()
            }.also {
                INSTANCE = it
            }
        }
    }
}
