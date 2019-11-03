package id.kotlin.movie.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R
import id.kotlin.movie.data.Result
import id.kotlin.movie.databinding.ActivityHomeBinding
import id.kotlin.movie.presentation.home.adapter.HomeAdapter
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), HomeViewModelCallback {

  @Inject
  lateinit var viewModel: HomeViewModel

  private lateinit var binding: ActivityHomeBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView<ActivityHomeBinding>(
        this,
        R.layout.activity_home
    ).apply {
      viewModel = this@HomeActivity.viewModel
    }
    viewModel.discoverMovie()
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.onDetach()
  }

  override fun onResponse(results: List<Result>) {
    binding.rvHome.adapter = HomeAdapter(results)
  }

  override fun onFailure(error: Throwable) {
    Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
  }
}