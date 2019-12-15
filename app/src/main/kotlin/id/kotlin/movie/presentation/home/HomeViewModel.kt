package id.kotlin.movie.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.kotlin.movie.data.home.HomeDatasource
import id.kotlin.movie.data.home.HomeFactory
import id.kotlin.movie.data.home.HomeResponse.Result
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val factory: HomeFactory
) : ViewModel(), HomeView {

  private val config = PagedList.Config.Builder().apply {
    setEnablePlaceholders(false)
    setPageSize(20)
  }.build()

  override val states: LiveData<HomeViewState>
    get() = Transformations.switchMap<HomeDatasource, HomeViewState>(
        factory.sourceLiveData,
        HomeDatasource::states
    )

  override val paged: LiveData<PagedList<Result>>
    get() = LivePagedListBuilder<Long, Result>(
        factory,
        config
    ).build()

  override fun onCleared() {
    super.onCleared()
    factory.disposables.clear()
  }

  override fun refresh() {
    factory.sourceLiveData.value?.invalidate()
  }
}