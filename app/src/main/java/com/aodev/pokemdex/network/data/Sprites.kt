package com.aodev.pokemdex.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sprites(
    @SerializedName("back_default")
    val backDefault: String?=null,
    @SerializedName("back_female")
    val backFemale: String?=null,
    @SerializedName("back_shiny")
    val backShiny: String?=null,
    @SerializedName("back_shiny_female")
    val backShinyFemale: String,
    @SerializedName("front_default")
    val frontDefault: String?=null,
    @SerializedName("front_female")
    val frontFemale: String?=null,
    @SerializedName("front_shiny")
    val frontShiny: String,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: String?=null,
    @SerializedName("other")
    val other: Other?=null,
    @SerializedName("versions")
    val versions: Versions?=null
): Parcelable
