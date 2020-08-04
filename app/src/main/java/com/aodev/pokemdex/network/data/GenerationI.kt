package com.aodev.pokemdex.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationI(
    @SerializedName("red-blue")
    val redBlue: RedBlue,
    @SerializedName("yellow")
    val yellow: Yellow
):Parcelable