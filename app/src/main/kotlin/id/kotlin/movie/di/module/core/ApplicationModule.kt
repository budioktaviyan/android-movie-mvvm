package id.kotlin.movie.di.module.core

import dagger.Module
import dagger.Provides
import id.kotlin.movie.data.home.HomeDatasource
import id.kotlin.movie.di.scope.Application
import retrofit2.Retrofit

@Module
class ApplicationModule {

  @Application
  @Provides
  fun providesHomeDatasource(retrofit: Retrofit): HomeDatasource =
      retrofit.create(HomeDatasource::class.java)
}