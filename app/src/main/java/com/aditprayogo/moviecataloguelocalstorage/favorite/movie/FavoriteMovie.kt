package com.aditprayogo.moviecataloguelocalstorage.favorite.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMovie(
    var id: Int = 0,
    var photo: String? = null,
    var title: String? = null,
    var overview: String? = null,
    var rating: Double? = 0.0
): Parcelable