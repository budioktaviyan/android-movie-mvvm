package id.kotlin.movie.data

import id.kotlin.movie.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeDatasource {

  @GET("/3/discover/movie")
  fun discoverMovie(
      @Query("api_key")
      apiKey: String = BuildConfig.API_KEY,

      @Query("page")
      page: Long = 1L
  ): Single<HomeResponse>
}