package com.example.pokemon.state

import android.os.Bundle
import com.example.pokemon.adapter.PokemonListAdapter

sealed class PokemonState {
    object Init : PokemonState()
    data class InitAdapter(val adapter: PokemonListAdapter) : PokemonState()
    data class Loading(val isLoading: Boolean) : PokemonState()
    data class ShowError(val msg: String) : PokemonState()
    data class NavToDetailsScreen(val data: Bundle) : PokemonState()
}
