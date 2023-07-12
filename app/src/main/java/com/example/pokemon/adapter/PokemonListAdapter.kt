package com.example.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.InflatePokemonListItemBinding
import com.example.pokemon.model.PokemonData

class PokemonListAdapter(
    private val pokemonList: MutableList<PokemonData>,
    private val onItemClicked: (data: PokemonData, pos: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = InflatePokemonListItemBinding.inflate(layoutInflater, parent, false)
        return PokemonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PokemonListViewHolder).bindData(pokemonList[position])
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    inner class PokemonListViewHolder(
        private val binding: InflatePokemonListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: PokemonData) {
            binding.data = data
            binding.containerPokemonItem.setOnClickListener {
                onItemClicked(data, adapterPosition)
            }
        }
    }
}
