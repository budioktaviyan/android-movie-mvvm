package id.kotlin.movie.presentation.home

import id.kotlin.movie.data.Result

interface HomeViewModelCallback {

  fun onResponse(results: List<Result>)
  fun onFailure(error: Throwable)
}