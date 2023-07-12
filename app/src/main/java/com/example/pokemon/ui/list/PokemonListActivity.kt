package com.example.pokemon.ui.list

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityPokemonListBinding
import com.example.pokemon.state.PokemonState
import com.example.pokemon.ui.view.PokemonDetailsActivity
import com.example.pokemon.util.showError
import com.example.pokemon.util.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonListBinding

    private val viewModel by viewModels<PokemonListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initListeners()
        subscribeObservers()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_list)
        binding.view = this
        binding.isProgressVisible = false

    }

    private fun initListeners() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etSearch.text.isNullOrEmpty().not()) {
                    viewModel.filterPokemonsByName(binding.etSearch.text.toString())
                } else {
                    viewModel.resetPokemonData()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun subscribeObservers() = viewModel.pokemonState.observe(this,
        Observer {
            when (it) {
                is PokemonState.ShowError -> showError(it.msg)
                is PokemonState.Loading ->
                    binding.isProgressVisible = it.isLoading

                is PokemonState.InitAdapter ->
                    binding.recyclerPokemons.adapter = it.adapter

                is PokemonState.NavToDetailsScreen -> navToPokemonDetails(it.data)

                else -> {}
            }
        })

    private fun navToPokemonDetails(data: Bundle) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtras(data)
        startActivity(intent)
    }

    fun openFilterDialog() {
        val options = arrayOf(getString(R.string.label_level), getString(R.string.label_hp))
        AlertDialog.Builder(this).setTitle(getString(R.string.label_sort_by))
            .setItems(options) { dialog: DialogInterface, which: Int ->
                when (which) {
                    0 -> {
                        viewModel.sortByLevel()
                        showMessage(getString(R.string.alert_sort_by_level))
                    }

                    1 -> {
                        viewModel.sortByHp()
                        showMessage(getString(R.string.alert_sort_by_hp))
                    }
                }
                dialog.dismiss()
            }.create().show()
    }

    fun resetList() = viewModel.resetPokemonData()
}
