package id.kotlin.movie.presentation.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R
import id.kotlin.movie.databinding.ActivityHomeBinding
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var viewModel: HomeViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DataBindingUtil.setContentView<ActivityHomeBinding>(
        this,
        R.layout.activity_home
    ).apply {
      viewModel = this@HomeActivity.viewModel
    }
    viewModel.discoverMovie()
  }
}