package com.aodev.pokemdex.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aodev.pokemdex.listener.HomeListener
import com.aodev.pokemdex.network.data.Pokemon
import com.aodev.pokemdex.network.data.Result
import com.aodev.pokemdex.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {

    private val _pokemonFetchAll = MutableLiveData<List<Result>>()
    val _pokemonList = MutableLiveData<List<Pokemon>>()
    private val list: ArrayList<Pokemon> = ArrayList()
    var homeListener: HomeListener? = null
    val pokemonRepository: PokemonRepository = PokemonRepository()
    val isConnectFalse = MutableLiveData<Boolean>()

    init {
        isConnectFalse.value =  false
        Log.e("HomeViewModel","init")
        if (list.size == 0) {
            homeListener?.fetchAllPokemon()
            fetchAllPokemon()
        } else {
            Log.e(TAG, "_pokemonFetchAll : " + list.size)
        }
    }

    fun fetchAllPokemon() {
        _pokemonFetchAll.value = ArrayList()
        _pokemonList.value = ArrayList()
        list.clear()
        homeListener?.fetchAllPokemon()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val allPokemon = pokemonRepository.fetchAllPokemon()
                withContext(Dispatchers.Main) {
                    if (allPokemon.isSuccessful) {
                        isConnectFalse.value =  false
                        _pokemonFetchAll.value = allPokemon.body()!!.results
                        fetchPokemonData();
                    } else {
                        isConnectFalse.value =  true
                        homeListener?.onFailure(allPokemon.errorBody().toString())
                    }
                }
            } catch (e: Exception) {
                isConnectFalse.postValue(true)
                Log.e(TAG, "fetchAllPokemon error : "+e.toString())
            }
        }

    }

    private fun fetchPokemonData() {
        val count = _pokemonFetchAll.value?.size
        Log.e(TAG, "_pokemonFetchAll : $count")
        var i: Int = 0
        _pokemonFetchAll.value?.forEach {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val Pokemon = pokemonRepository.fetchPokemonData(it.url)
                    withContext(Dispatchers.Main) {
                        if (Pokemon.isSuccessful) {
                            isConnectFalse.value =  false
                            list.add(Pokemon.body()!!)
                            _pokemonList.value = list
                        } else {
                            isConnectFalse.value =  true
                            homeListener?.onFailure(Pokemon.errorBody().toString())
                        }
                    }
                } catch (e: Exception) {
                    isConnectFalse.postValue(true)
                    Log.e(TAG, "fetchPokemonData error : "+e.toString())
                }
            }
        }

    }

    fun LoadPokemonFinish() {
        homeListener?.onSuccess()
    }
}