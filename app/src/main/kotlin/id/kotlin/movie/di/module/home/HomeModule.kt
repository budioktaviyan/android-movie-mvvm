package id.kotlin.movie.di.module.home

import dagger.Module
import dagger.Provides
import id.kotlin.movie.data.HomeDatasource
import retrofit2.Retrofit

@Module
abstract class HomeModule {

  @Module
  companion object {

    @JvmStatic
    @Provides
    fun providesHomeDatasource(retrofit: Retrofit): HomeDatasource =
        retrofit.create(HomeDatasource::class.java)
  }
}