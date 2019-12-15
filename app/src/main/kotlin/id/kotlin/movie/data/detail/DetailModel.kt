package id.kotlin.movie.data.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailModel(
    val title: String,
    val overview: String,
    val backdropPath: String
) : Parcelable