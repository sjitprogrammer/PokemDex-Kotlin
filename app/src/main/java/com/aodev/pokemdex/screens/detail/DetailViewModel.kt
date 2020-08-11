package com.aodev.pokemdex.screens.detail

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aodev.pokemdex.network.data.Ability
import com.aodev.pokemdex.network.data.Pokemon
import com.aodev.pokemdex.network.data.Stat
import com.aodev.pokemdex.network.data.Type
import kotlinx.android.synthetic.main.fragment_detail.*

private val TAG = "DetailViewModel"

class DetailViewModel(pokemon: Pokemon) : ViewModel() {
    private val _pokemon = MutableLiveData<Pokemon>()
    var hp = 0
    var attack = 0
    var defense = 0
    var specialAttack = 0
    var specialDefense = 0
    var speed = 0
    var totalStat = 0

    init {
        _pokemon.value = pokemon
        setPokemonStat()
    }

    fun getPokemonId(): Int {
        return _pokemon.value!!.id
    }

    fun pokemonNumber(): String {
        return "Number : " + _pokemon.value?.id
    }

    fun pokemonName(): String {
        return _pokemon.value!!.name
    }

    fun pokemonHeight(): String {
        val height = _pokemon.value?.height
        val textHeight = (height!! * 0.1).toFloat().toString() + " m"
        return textHeight
    }

    fun pokemonWeight(): String {
        return _pokemon.value?.weight.toString() + " lbs"
    }

    fun pokemonBaseExperience(): String {
        return _pokemon.value?.baseExperience.toString()
    }

    fun pokemonAbility(): List<Ability> {
        return _pokemon.value!!.abilities
    }

    fun pokemonType(): List<Type> {
        return _pokemon.value!!.types
    }

    fun setPokemonStat(){
        val stats = _pokemon.value!!.stats
        var total = 0;
        stats.forEach {
            val stat = it.stat.name
            val value = it.baseStat
            total += value
            if (stat.equals("hp")) {
                hp = value
            } else if (stat.equals("attack")) {
                attack = value
            } else if (stat.equals("defense")) {
                defense = value
            } else if (stat.equals("special-attack")) {
                specialAttack = value
            } else if (stat.equals("special-defense")) {
                specialDefense = value
            } else if (stat.equals("speed")) {
                speed = value
            }
        }
        totalStat = total
    }
}