package com.example.pokemon.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityPokemonDetailsBinding
import com.example.pokemon.databinding.InflateAbilityItemBinding
import com.example.pokemon.databinding.InflateAttacksItemBinding
import com.example.pokemon.databinding.InflateWeaknessItemBinding
import com.example.pokemon.model.Ability
import com.example.pokemon.model.Attack
import com.example.pokemon.model.PokemonData
import com.example.pokemon.model.Resistance
import com.example.pokemon.model.Weaknesse
import com.example.pokemon.util.Constants
import com.example.pokemon.util.inflateDataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding

    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_details)
        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun initData() {
        intent.extras?.run {
            getParcelable<PokemonData>(Constants.BUNDLE_KEY_POKEMON_DATA)?.let {
                binding.data = it
                initAttackLayout(it.attacks)
                initWeaknessLayout(it.weaknesses)
                initAbilitiesLayout(it.abilities)
                initResistenceLayout(it.resistances)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initAttackLayout(attacks: MutableList<Attack>?) {
        if (attacks.isNullOrEmpty()) {
            binding.isAttacksVisible = false
        } else {
            binding.isAttacksVisible = true
            binding.containerAttacks.removeAllViews()
            attacks.forEach {
                val data = if (it.cost.isNullOrEmpty().not()) {
                    it.cost!!.joinToString(",")
                } else ""
                val attackLayBinding = getAttackLayoutBinding()
                attackLayBinding.tvName.text = "Name: ${it.name ?: ""}"
                attackLayBinding.tvCost.text = "Cost: $data"
                attackLayBinding.tvText.text = "Text: ${it.text ?: ""}"
                attackLayBinding.tvDamage.text = "Damage: ${it.damage ?: ""}"
                attackLayBinding.tvConEnergyCost.text =
                    "ConvertedEnergyCost: ${it.convertedEnergyCost}"
                binding.containerAttacks.addView(attackLayBinding.root)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initWeaknessLayout(weaknesses: MutableList<Weaknesse>?) {
        if (weaknesses.isNullOrEmpty()) {
            binding.isWeaknessVisible = false
        } else {
            binding.isWeaknessVisible = true
            binding.containerWeakness.removeAllViews()
            weaknesses.forEach {
                val weaknessBinding = getWeaknessLayoutBinding()
                weaknessBinding.tvType.text = "Type: ${it.type ?: ""}"
                weaknessBinding.tvSubType.text = "Value: ${it.value ?: ""}"
                binding.containerWeakness.addView(weaknessBinding.root)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initAbilitiesLayout(abilities: MutableList<Ability>?) {
        if (abilities.isNullOrEmpty()) {
            binding.isAbilityVisible = false
        } else {
            binding.isAbilityVisible = true
            binding.containerAbility.removeAllViews()
            abilities.forEach {
                val abilitiesBinding = getAbilityLayoutBinding()
                abilitiesBinding.tvName.text = "Name ${it.name ?: ""}"
                abilitiesBinding.tvText.text = "Text ${it.text ?: ""}"
                abilitiesBinding.tvType.text = "Type ${it.type ?: ""}"
                binding.containerAbility.addView(abilitiesBinding.root)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initResistenceLayout(resistances: MutableList<Resistance>?) {
        if (resistances.isNullOrEmpty()) {
            binding.isResistanceVisible = false
        } else {
            binding.isResistanceVisible = true
            binding.containerResistence.removeAllViews()
            resistances.forEach {
                val resistenceBinding = getWeaknessLayoutBinding()
                resistenceBinding.tvType.text = "Type ${it.type ?: ""}"
                resistenceBinding.tvSubType.text = "Value ${it.value ?: ""}"
                binding.containerResistence.addView(resistenceBinding.root)
            }
        }
    }

    private fun getAttackLayoutBinding() =
        inflateDataBinding(R.layout.inflate_attacks_item) as InflateAttacksItemBinding

    private fun getWeaknessLayoutBinding() =
        inflateDataBinding(R.layout.inflate_weakness_item) as InflateWeaknessItemBinding

    private fun getAbilityLayoutBinding() =
        inflateDataBinding(R.layout.inflate_ability_item) as InflateAbilityItemBinding

}