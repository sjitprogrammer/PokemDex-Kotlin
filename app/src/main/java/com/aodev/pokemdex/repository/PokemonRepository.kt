package com.aodev.pokemdex.repository

import com.aodev.pokemdex.network.MyApi
import com.aodev.pokemdex.network.data.Pokemon
import com.aodev.pokemdex.network.data.PokemonList
import retrofit2.Response

class PokemonRepository {

    suspend fun fetchAllPokemon():Response<PokemonList>{
        return MyApi.retrofitService.fetchAllPokemon()
    }

    suspend fun fetchPokemonData(url:String):Response<Pokemon>{
        return MyApi.retrofitService.fetchPokemonData(url)
    }
}