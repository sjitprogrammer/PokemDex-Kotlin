package com.aodev.pokemdex.network.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VersionX(
    val name: String,
    val url: String
):Parcelable