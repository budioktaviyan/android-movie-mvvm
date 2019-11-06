package id.kotlin.movie.presentation.home

import androidx.lifecycle.LiveData

interface HomeView {

  val states: LiveData<HomeViewState>

  fun discoverMovie()
  fun loadMore(page: Long)
  fun reload()
}