package id.kotlin.movie.di.builder

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.kotlin.movie.di.factory.ViewModelFactory
import id.kotlin.movie.di.module.detail.DetailModule
import id.kotlin.movie.di.module.home.HomeModule
import id.kotlin.movie.presentation.detail.DetailActivity
import id.kotlin.movie.presentation.home.HomeActivity

@Module
abstract class ActivityBuilder {

  @Suppress("unused")
  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Suppress("unused")
  @ContributesAndroidInjector(modules = [HomeModule::class])
  abstract fun contributeHomeActivity(): HomeActivity

  @Suppress("unused")
  @ContributesAndroidInjector(modules = [DetailModule::class])
  abstract fun contributeDetailActivity(): DetailActivity
}