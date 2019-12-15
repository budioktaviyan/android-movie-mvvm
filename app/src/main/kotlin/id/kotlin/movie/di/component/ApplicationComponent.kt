package id.kotlin.movie.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import id.kotlin.movie.MovieApp
import id.kotlin.movie.di.builder.ActivityBuilder
import id.kotlin.movie.di.module.core.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AndroidSupportInjectionModule::class,
  NetworkModule::class,
  ActivityBuilder::class
])
interface ApplicationComponent : AndroidInjector<MovieApp>