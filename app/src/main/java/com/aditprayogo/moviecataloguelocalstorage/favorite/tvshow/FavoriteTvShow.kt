package com.aditprayogo.moviecataloguelocalstorage.favorite.tvshow

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteTvShow(
    var id: Int = 0,
    var photo: String? = null,
    var title: String? = null,
    var overview: String? = null,
    var rating: Double? = 0.0
): Parcelable