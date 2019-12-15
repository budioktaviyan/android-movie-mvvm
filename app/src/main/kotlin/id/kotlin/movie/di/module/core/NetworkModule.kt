package id.kotlin.movie.di.module.core

import dagger.Module
import dagger.Provides
import id.kotlin.movie.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Singleton
  @Provides
  fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
      HttpLoggingInterceptor().apply {
        level = when (BuildConfig.DEBUG) {
          true -> BODY
          false -> NONE
        }
      }

  @Singleton
  @Provides
  fun providesHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
      OkHttpClient.Builder().apply {
        retryOnConnectionFailure(true)
        addInterceptor(interceptor)
      }.build()

  @Singleton
  @Provides
  fun providesHttpAdapter(client: OkHttpClient): Retrofit =
      Retrofit.Builder().apply {
        client(client)
        baseUrl(BuildConfig.BASE_URL)
        addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        addConverterFactory(GsonConverterFactory.create())
      }.build()
}