package com.aodev.pokemdex.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeX
): Parcelable