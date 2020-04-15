package id.kotlin.movie.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.data.home.HomeResponse.Result
import id.kotlin.movie.databinding.ActivityHomeBinding
import id.kotlin.movie.presentation.detail.DetailActivity
import id.kotlin.movie.presentation.home.HomeViewState.Failed
import id.kotlin.movie.presentation.home.HomeViewState.Loading
import id.kotlin.movie.presentation.home.adapter.HomeAdapter
import id.kotlin.movie.presentation.home.adapter.HomeAdapter.HomeAdapterCallback
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.LOADING
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.RESULT
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), HomeAdapterCallback {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel: HomeView by lazy {
    ViewModelProvider(
        this,
        viewModelFactory
    )[HomeViewModel::class.java]
  }

  private lateinit var binding: ActivityHomeBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val adapter = HomeAdapter(callback = this)
    binding.rvHome.adapter = adapter
    binding.rvHome.setHasFixedSize(true)
    (binding.rvHome.layoutManager as GridLayoutManager).apply {
      spanSizeLookup = object : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int =
            when (adapter.getItemViewType(position)) {
              RESULT.ordinal -> 1
              LOADING.ordinal -> 2
              else -> throw RuntimeException("Illegal view type")
            }
      }
    }

    binding.srlHome.setColorSchemeColors(
        ContextCompat.getColor(
            this,
            R.color.black
        )
    )
    binding.srlHome.setOnRefreshListener { viewModel.refresh() }

    viewModel.states.observe(this, Observer { state ->
      adapter.state = state
      if (state is Failed) Log.e(HomeActivity::class.java.simpleName, "${state.error}")
      binding.pbHome.visibility = if (state is Loading) VISIBLE else GONE
      binding.srlHome.isRefreshing = false
    })

    viewModel.paged.observe(this, Observer { page ->
      adapter.submitList(page)
    })
  }

  override fun onClick(result: Result) {
    startActivity(
        Intent(
            this,
            DetailActivity::class.java
        ).apply {
          putExtra(
              DetailActivity::class.java.simpleName,
              DetailModel(
                  title = result.title ?: "Untitled",
                  overview = result.overview ?: "No Description",
                  backdropPath = result.backdropPath ?: "untitled.jpg"
              )
          )
        }
    )
  }
}