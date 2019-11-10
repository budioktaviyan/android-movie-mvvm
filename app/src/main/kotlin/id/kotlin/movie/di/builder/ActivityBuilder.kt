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

@Suppress("unused")
@Module
abstract class ActivityBuilder {

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @ContributesAndroidInjector(modules = [HomeModule::class])
  abstract fun contributeHomeActivity(): HomeActivity

  @ContributesAndroidInjector(modules = [DetailModule::class])
  abstract fun contributeDetailActivity(): DetailActivity
}