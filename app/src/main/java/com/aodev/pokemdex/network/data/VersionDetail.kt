package com.aodev.pokemdex.network.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VersionDetail(
    val rarity: Int,
    val version: VersionX
):Parcelable