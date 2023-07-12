package com.example.pokemon.ui.list

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemon.adapter.PokemonListAdapter
import com.example.pokemon.base.NetworkResult
import com.example.pokemon.model.PokemonData
import com.example.pokemon.repo.PokemonRepository
import com.example.pokemon.state.PokemonState
import com.example.pokemon.util.Constants
import com.example.pokemon.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
    application: Application
) : AndroidViewModel(application) {

    val pokemonState: SingleLiveEvent<PokemonState> by lazy {
        SingleLiveEvent<PokemonState>()
    }

    private lateinit var pokemonListAdapter: PokemonListAdapter
    private val pokemonData = mutableListOf<PokemonData>()

    private val tempPokemonData = mutableListOf<PokemonData>()

    init {
        initAdapter()
        getPokemonList()
    }

    private fun initAdapter() {
        pokemonListAdapter = PokemonListAdapter(pokemonData) { data, pos ->
            pokemonState.setValue(PokemonState.NavToDetailsScreen(Bundle().apply {
                putParcelable(Constants.BUNDLE_KEY_POKEMON_DATA, data)
            }))
        }
    }

    private fun getPokemonList() {
        pokemonState.setValue(PokemonState.Loading(true))
        viewModelScope.launch(Dispatchers.IO) {
            repository.getListOfPokemons().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        withContext(Dispatchers.Main) {
                            pokemonState.setValue(PokemonState.Loading(false))
                            it.data?.let { response ->
                                if (response.data.isNullOrEmpty().not()) {
                                    pokemonState.setValue(
                                        PokemonState.InitAdapter(
                                            pokemonListAdapter
                                        )
                                    )
                                    pokemonData.clear()
                                    pokemonData.addAll(response.data!!)
                                    tempPokemonData.clear()
                                    tempPokemonData.addAll(response.data!!)
                                    pokemonListAdapter.notifyDataSetChanged()
                                } else {
                                    pokemonState.setValue(PokemonState.ShowError("Something went wrong"))
                                }
                            } ?: kotlin.run {
                                pokemonState.setValue(PokemonState.ShowError("Something went wrong"))
                            }
                        }
                    }

                    is NetworkResult.Error -> {
                        withContext(Dispatchers.Main) {
                            pokemonState.setValue(PokemonState.Loading(false))
                            pokemonState.setValue(
                                PokemonState.ShowError(it.message ?: "Something went wrong")
                            )
                        }
                    }
                }
            }
        }
    }

    fun filterPokemonsByName(name: String) {
        val filteredList = pokemonData.filter { it.name!!.startsWith(name, true) }
        pokemonData.clear()
        pokemonData.addAll(filteredList)
        pokemonListAdapter.notifyDataSetChanged()
    }

    fun resetPokemonData() {
        pokemonData.clear()
        pokemonData.addAll(tempPokemonData)
        pokemonListAdapter.notifyDataSetChanged()
    }

    fun sortByLevel() {
        resetPokemonData()
        pokemonData.sortBy { it.level?.toInt() ?: Int.MAX_VALUE }
        pokemonListAdapter.notifyDataSetChanged()
    }

    fun sortByHp() {
        resetPokemonData()
        pokemonData.sortBy { it.hp?.toInt() ?: Int.MAX_VALUE }
        pokemonListAdapter.notifyDataSetChanged()
    }

}