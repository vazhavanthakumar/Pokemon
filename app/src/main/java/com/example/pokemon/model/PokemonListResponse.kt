package com.example.pokemon.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PokemonListResponse(
    var count: Int? = null,
    var data: MutableList<PokemonData>? = null,
    var page: Int? = null,
    var pageSize: Int? = null,
    var totalCount: Int? = null
)

@Parcelize
data class PokemonData(
    var abilities: MutableList<Ability>? = null,
    var attacks: MutableList<Attack>? = null,
    var hp: String? = null,
    var id: String? = null,
    var images: Images? = null,
    var level: String? = null,
    var name: String? = null,
    var resistances: MutableList<Resistance>? = null,
    var subtypes: MutableList<String>? = null,
    var types: MutableList<String>? = null,
    var weaknesses: MutableList<Weaknesse>? = null
): Parcelable

@Parcelize
data class Ability(
    var name: String? = null,
    var text: String? = null,
    var type: String? = null
): Parcelable

@Parcelize
data class Attack(
    var convertedEnergyCost: Int? = null,
    var cost: List<String?>? = null,
    var damage: String? = null,
    var name: String? = null,
    var text: String? = null
): Parcelable


@Parcelize
data class Resistance(
    var type: String? = null,
    var value: String? = null
):Parcelable

@Parcelize
data class Images(
    var logo: String? = null,
    var symbol: String? = null,
    var small: String? = null,
    var large: String? = null
):Parcelable

@Parcelize
data class Weaknesse(
    var type: String? = null,
    var value: String? = null
):Parcelable
