package id.kotlin.movie.data.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import id.kotlin.movie.data.home.HomeResponse.Result
import id.kotlin.movie.presentation.home.HomeViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class HomeDatasource(
    private val service: HomeService,
    private val disposables: CompositeDisposable
) : PageKeyedDataSource<Long, Result>() {

  val states: MutableLiveData<HomeViewState> = MutableLiveData()

  private var totalPages: Long = 1L

  override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Result>) {
    service.discoverMovie(page = 1)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { updateStates(HomeViewState.Loading) }
        .doOnSuccess { response ->
          totalPages = response.totalPages
          val page = response.page.inc()
          val results = response.results.toMutableList().also { it.add(response.results.last()) }
          callback.onResult(results, null, page)
          updateStates(HomeViewState.Done)
        }
        .doOnError { error ->
          val value = HomeViewState::Failed.invoke(error)
          updateStates(value)
        }
        .subscribe()
        .addTo(disposables)
  }

  override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Result>) {
    if (params.key > totalPages) return
    service.discoverMovie(page = params.key)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { updateStates(HomeViewState.Page) }
        .doOnSuccess { response ->
          val results = response.results
          callback.onResult(results, params.key.inc())
          updateStates(HomeViewState.Done)
        }
        .doOnError { error ->
          val value = HomeViewState::Failed.invoke(error)
          updateStates(value)
        }
        .subscribe()
        .addTo(disposables)
  }

  override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Result>) {}

  private fun updateStates(states: HomeViewState) {
    this.states.postValue(states)
  }
}