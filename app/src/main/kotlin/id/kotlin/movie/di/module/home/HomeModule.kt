package id.kotlin.movie.di.module.home

import dagger.Binds
import dagger.Module
import dagger.Provides
import id.kotlin.movie.data.HomeDatasource
import id.kotlin.movie.presentation.home.HomeActivity
import id.kotlin.movie.presentation.home.HomeViewModel
import id.kotlin.movie.presentation.home.HomeViewModelCallback
import retrofit2.Retrofit

@Module
abstract class HomeModule {

  @Module
  companion object {

    @JvmStatic
    @Provides
    fun providesHomeDatasource(retrofit: Retrofit): HomeDatasource =
        retrofit.create(HomeDatasource::class.java)

    @JvmStatic
    @Provides
    fun providesHomeViewModel(
        callback: HomeViewModelCallback,
        datasource: HomeDatasource
    ): HomeViewModel = HomeViewModel(callback, datasource)
  }

  @Suppress("unused")
  @Binds
  abstract fun bindHomeViewModelCallback(activity: HomeActivity): HomeViewModelCallback
}