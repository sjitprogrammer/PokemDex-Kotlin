package com.aodev.pokemdex.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aodev.pokemdex.network.data.Pokemon

class DetailViewModelFactory(private val pokemon: Pokemon) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(pokemon) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}