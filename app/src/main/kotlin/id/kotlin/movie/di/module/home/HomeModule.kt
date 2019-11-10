package id.kotlin.movie.di.module.home

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.kotlin.movie.di.scope.ViewModelKey
import id.kotlin.movie.presentation.home.HomeViewModel

@Suppress("unused")
@Module
abstract class HomeModule {

  @Binds
  @IntoMap
  @ViewModelKey(HomeViewModel::class)
  abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}