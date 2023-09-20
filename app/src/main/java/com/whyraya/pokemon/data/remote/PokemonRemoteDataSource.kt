package com.whyraya.pokemon.data.remote

import androidx.paging.PagingData
import com.whyraya.pokemon.model.response.PokemonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(private val apiServices: PokemonApiServices) {

    fun getPokemon(): Flow<PagingData<PokemonResponse>> = withPager(pageSize = LIMIT) { page ->
        apiServices.getPokemon(offset = page * LIMIT, limit = LIMIT).body()?.results.orEmpty()
    }.flow

    suspend fun getPokemonById(id: Int): PokemonResponse = getResponse {
        apiServices.getPokemonById(id)
    }

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): T = try {
        val result = request.invoke()
        if (result.isSuccessful) {
            result.body() ?: throw Throwable(UNKNOWN_ERROR_MESSAGE)
        } else {
            throw Throwable(Throwable(result.message()))
        }
    } catch (error: UnknownHostException) {
        throw error
    } catch (error: ConnectException) {
        throw error
    } catch (error: HttpException) {
        throw error
    } catch (error: Throwable) {
        throw error
    }

    companion object {
        private const val LIMIT = 20
        private const val UNKNOWN_ERROR_MESSAGE = "UNKNOWN_ERROR_MESSAGE"
    }
}
