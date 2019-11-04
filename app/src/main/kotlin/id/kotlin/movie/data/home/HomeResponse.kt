package id.kotlin.movie.data.home

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("page")
    val page: Long = -1L,

    @SerializedName("total_pages")
    val totalPages: Long = -1L,

    @SerializedName("results")
    val results: List<Result?> = emptyList()
)

data class Result(
    @SerializedName("title")
    val title: String? = "",

    @SerializedName("overview")
    val overview: String? = "",

    @SerializedName("poster_path")
    val posterPath: String? = "",

    @SerializedName("backdrop_path")
    val backdropPath: String? = "",

    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0
)