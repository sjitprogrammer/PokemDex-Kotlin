package com.aodev.pokemdex.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoveX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
): Parcelable