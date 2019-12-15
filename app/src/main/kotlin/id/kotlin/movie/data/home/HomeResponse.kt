package id.kotlin.movie.data.home

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("page")
    val page: Long,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("results")
    val results: List<Result?> = emptyList()
) {

  data class Result(
      @SerializedName("id")
      val id: Long? = 1L,

      @SerializedName("title")
      val title: String? = "Untitled",

      @SerializedName("overview")
      val overview: String? = "No Description",

      @SerializedName("poster_path")
      val posterPath: String? = "untitled.jpg",

      @SerializedName("backdrop_path")
      val backdropPath: String? = "untitled.jpg",

      @SerializedName("vote_average")
      val voteAverage: Double? = 0.0
  )
}