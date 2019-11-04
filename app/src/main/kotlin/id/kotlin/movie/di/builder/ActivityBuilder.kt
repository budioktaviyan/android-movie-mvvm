package id.kotlin.movie.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.kotlin.movie.di.module.detail.DetailModule
import id.kotlin.movie.di.module.home.HomeModule
import id.kotlin.movie.di.scope.Presentation
import id.kotlin.movie.presentation.detail.DetailActivity
import id.kotlin.movie.presentation.home.HomeActivity

@Module
abstract class ActivityBuilder {

  @Suppress("unused")
  @Presentation
  @ContributesAndroidInjector(modules = [HomeModule::class])
  abstract fun contributeHomeActivity(): HomeActivity

  @Suppress("unused")
  @Presentation
  @ContributesAndroidInjector(modules = [DetailModule::class])
  abstract fun contributeDetailActivity(): DetailActivity
}