package id.kotlin.movie.presentation.home

import id.kotlin.movie.data.home.HomeResponse

sealed class HomeViewState {

  object Loading : HomeViewState()

  data class Success(val response: HomeResponse) : HomeViewState()
  data class Error(val error: Throwable) : HomeViewState()

  data class PaginationSuccess(val response: HomeResponse) : HomeViewState()
  data class PaginationError(val error: Throwable) : HomeViewState()

  data class ReloadSuccess(val response: HomeResponse) : HomeViewState()
  data class ReloadError(val error: Throwable) : HomeViewState()
}