package com.aodev.pokemdex.network.data


import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any?=null,
    @SerializedName("results")
    val results: List<Result>
)