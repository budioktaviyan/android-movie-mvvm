package id.kotlin.movie.presentation.home

interface HomeView {

  fun discoverMovie()
  fun onDetach()
  fun loadMore(page: Long)
  fun reload()
}