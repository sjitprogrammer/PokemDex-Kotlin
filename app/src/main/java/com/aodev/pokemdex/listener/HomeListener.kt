package com.aodev.pokemdex.listener

interface HomeListener {
    fun fetchAllPokemon()
    fun onSuccess()
    fun onFailure(message: String)
}