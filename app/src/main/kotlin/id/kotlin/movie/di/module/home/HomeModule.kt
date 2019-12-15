package id.kotlin.movie.di.module.home

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import id.kotlin.movie.data.home.HomeFactory
import id.kotlin.movie.data.home.HomeService
import id.kotlin.movie.di.scope.ViewModelKey
import id.kotlin.movie.presentation.home.HomeViewModel
import retrofit2.Retrofit

@Suppress("unused")
@Module
abstract class HomeModule {

  @Module
  companion object {

    @JvmStatic
    @Provides
    fun providesHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @JvmStatic
    @Provides
    fun providesHomeFactory(service: HomeService): HomeFactory =
        HomeFactory(service)
  }

  @Binds
  @IntoMap
  @ViewModelKey(HomeViewModel::class)
  abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}