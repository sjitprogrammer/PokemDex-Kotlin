package com.aodev.pokemdex.network

import com.aodev.pokemdex.network.data.Pokemon
import com.aodev.pokemdex.network.data.PokemonList
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

private const val BASE_URL = "https://pokeapi.co/api/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("pokemon?limit=151")
    fun fetchAllPokemon() : Call<PokemonList>

    @GET("pokemon/{id}")
    fun fetchPokemon(@Path("id") id:Int) : Call<Pokemon>

    @GET
    fun fetchPokemonData(@Url url: String?): Call<Pokemon>
}

object MyApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}