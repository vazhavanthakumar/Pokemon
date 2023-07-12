package com.example.pokemon.api

import com.example.pokemon.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/cards")
    suspend fun getLisOfPokemon(
        @Query("pageSize") pageSize: Int
    ): Response<PokemonListResponse>
}