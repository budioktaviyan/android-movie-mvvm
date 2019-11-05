package id.kotlin.movie.presentation.home

import id.kotlin.movie.data.home.HomeResponse

interface HomeViewModelCallback {

  fun onResponse(response: HomeResponse)
  fun onFailure(error: Throwable)

  fun onPaginationSuccess(response: HomeResponse)
  fun onPaginationError(error: Throwable)

  fun onReloadSuccess(response: HomeResponse)
  fun onReloadError(error: Throwable)
}