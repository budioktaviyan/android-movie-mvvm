package id.kotlin.movie.presentation.home

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R

class HomeActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
  }
}