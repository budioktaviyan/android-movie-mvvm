package id.kotlin.movie.presentation.home

import id.kotlin.movie.data.home.HomeResponse

interface HomeViewModelCallback {

  fun onResponse(response: HomeResponse)
  fun onFailure(error: Throwable)
  fun onPagination(response: HomeResponse)
}