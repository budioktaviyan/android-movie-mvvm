package id.kotlin.movie.presentation.home

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.kotlin.movie.data.home.HomeResponse.Result

interface HomeView {

  val states: LiveData<HomeViewState>
  val paged: LiveData<PagedList<Result>>

  fun refresh()
}