package com.example.pokemon.api

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getListOfPokemons() = apiService.getLisOfPokemon(20)

}