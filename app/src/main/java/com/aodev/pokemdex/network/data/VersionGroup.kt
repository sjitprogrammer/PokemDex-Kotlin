package com.aodev.pokemdex.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VersionGroup(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
):Parcelable