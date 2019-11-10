package id.kotlin.movie.di.module.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.kotlin.movie.di.scope.ViewModelKey
import id.kotlin.movie.presentation.detail.DetailViewModel

@Suppress("unused")
@Module
abstract class DetailModule {

  @Binds
  @IntoMap
  @ViewModelKey(DetailViewModel::class)
  abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel
}