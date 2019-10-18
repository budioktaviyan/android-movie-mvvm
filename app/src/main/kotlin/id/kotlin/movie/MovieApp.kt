package id.kotlin.movie

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import id.kotlin.movie.di.component.DaggerApplicationComponent

class MovieApp : DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
      DaggerApplicationComponent.create().apply { inject(this@MovieApp) }
}