package com.aodev.pokemdex.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld
):Parcelable