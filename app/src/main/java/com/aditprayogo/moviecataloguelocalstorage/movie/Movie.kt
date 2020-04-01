package com.aditprayogo.moviecataloguelocalstorage.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var photo: String,
    var title: String,
    var overview: String,
    var rating: Double
): Parcelable
