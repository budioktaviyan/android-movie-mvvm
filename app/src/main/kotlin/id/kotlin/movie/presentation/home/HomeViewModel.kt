package id.kotlin.movie.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.kotlin.movie.data.home.HomeDatasource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val datasource: HomeDatasource
) : ViewModel(), HomeView {

  private val disposables: CompositeDisposable = CompositeDisposable()
  private val observer = MutableLiveData<HomeViewState>()

  override val states: LiveData<HomeViewState>
    get() = observer

  override fun onCleared() {
    disposables.clear()
    super.onCleared()
  }

  override fun discoverMovie() {
    datasource.discoverMovie()
        .map<HomeViewState>(HomeViewState::Success)
        .onErrorReturn(HomeViewState::Error)
        .toFlowable()
        .startWith(HomeViewState.Loading)
        .subscribe(observer::postValue)
        .let(disposables::add)
  }

  override fun loadMore(page: Long) {
    datasource.discoverMovie(page = page)
        .map<HomeViewState>(HomeViewState::PaginationSuccess)
        .onErrorReturn(HomeViewState::PaginationError)
        .toFlowable()
        .subscribe(observer::postValue)
        .let(disposables::add)
  }

  override fun reload() {
    datasource.discoverMovie()
        .map<HomeViewState>(HomeViewState::ReloadSuccess)
        .onErrorReturn(HomeViewState::ReloadError)
        .toFlowable()
        .subscribe(observer::postValue)
        .let(disposables::add)
  }
}