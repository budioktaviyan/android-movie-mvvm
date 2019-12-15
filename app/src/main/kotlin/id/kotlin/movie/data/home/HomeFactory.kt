package id.kotlin.movie.data.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.kotlin.movie.data.home.HomeResponse.Result
import io.reactivex.disposables.CompositeDisposable

class HomeFactory(
    private val service: HomeService
) : DataSource.Factory<Long, Result>() {

  val disposables: CompositeDisposable = CompositeDisposable()
  val sourceLiveData: MutableLiveData<HomeDatasource> = MutableLiveData()

  override fun create(): DataSource<Long, Result> =
      HomeDatasource(service, disposables).also { datasource ->
        sourceLiveData.postValue(datasource)
      }
}