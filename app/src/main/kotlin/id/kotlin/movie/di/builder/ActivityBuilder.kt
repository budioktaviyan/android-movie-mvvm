package id.kotlin.movie.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.kotlin.movie.di.module.home.HomeModule
import id.kotlin.movie.di.scope.Presentation
import id.kotlin.movie.presentation.home.HomeActivity

@Module
abstract class ActivityBuilder {

  @Suppress("unused")
  @Presentation
  @ContributesAndroidInjector(modules = [HomeModule::class])
  abstract fun contributeHomeActivity(): HomeActivity
}