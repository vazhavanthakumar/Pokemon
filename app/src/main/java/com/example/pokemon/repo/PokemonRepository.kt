package com.example.pokemon.repo

import com.example.pokemon.api.ApiHelperImpl
import com.example.pokemon.base.BaseApiResponse
import com.example.pokemon.base.NetworkResult
import com.example.pokemon.model.PokemonListResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class PokemonRepository @Inject constructor(private val apiHelperImpl: ApiHelperImpl) :
    BaseApiResponse() {

        suspend fun getListOfPokemons() = flow<NetworkResult<PokemonListResponse>> {
            emit(safeApiCall { apiHelperImpl.getListOfPokemons() })
        }.flowOn(Dispatchers.IO)
}