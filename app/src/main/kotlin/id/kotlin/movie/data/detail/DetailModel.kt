package id.kotlin.movie.data.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailModel(
    val title: String? = null,
    val overview: String? = null,
    val backdropPath: String? = null
) : Parcelable