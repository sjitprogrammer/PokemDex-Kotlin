package com.aodev.pokemdex.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Versions(
    @SerializedName("generation-i")
    val generationI: GenerationI
):Parcelable