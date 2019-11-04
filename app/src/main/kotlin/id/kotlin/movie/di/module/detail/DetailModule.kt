package id.kotlin.movie.di.module.detail

import dagger.Module
import dagger.Provides
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.presentation.detail.DetailViewModel

@Module
class DetailModule {

  @Provides
  fun providesDetailModel(): DetailModel = DetailModel()

  @Provides
  fun providesDetailViewModel(model: DetailModel): DetailViewModel =
      DetailViewModel(model)
}