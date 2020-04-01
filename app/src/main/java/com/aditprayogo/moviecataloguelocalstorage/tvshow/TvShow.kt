package com.aditprayogo.moviecataloguelocalstorage.tvshow

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(
    var id: Int,
    var photo: String,
    var title: String,
    var overview: String,
    var rating: Double
): Parcelable