package com.aodev.pokemdex.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonItem(val title: String? = "", val imageUrl: String? = "", val number: Int? = 0) :
    Parcelable