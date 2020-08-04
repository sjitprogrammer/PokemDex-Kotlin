package com.aodev.pokemdex.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameIndice(
    @SerializedName("game_index")
    val gameIndex: Int,
    @SerializedName("version")
    val version: Version
): Parcelable