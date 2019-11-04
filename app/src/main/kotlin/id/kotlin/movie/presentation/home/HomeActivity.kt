package id.kotlin.movie.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R
import id.kotlin.movie.data.HomeResponse
import id.kotlin.movie.databinding.ActivityHomeBinding
import id.kotlin.movie.presentation.home.adapter.HomeAdapter
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.LOADING
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.RESULT
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), HomeViewModelCallback {

  @Inject
  lateinit var viewModel: HomeViewModel

  private lateinit var binding: ActivityHomeBinding
  private lateinit var adapter: HomeAdapter

  private var isLoading: Boolean = false
  private var currentPage: Long = -1L

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = DataBindingUtil.setContentView<ActivityHomeBinding>(
        this,
        R.layout.activity_home)
        .apply { viewModel = this@HomeActivity.viewModel }
        .also { viewModel.discoverMovie() }
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.onDetach()
  }

  override fun onResponse(response: HomeResponse) {
    adapter = HomeAdapter(response.results.filterNotNull().toMutableList())
    binding.rvHome.adapter = adapter

    currentPage = response.page
    binding.rvHome.addOnScrollListener(object : OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount

        if (currentPage < response.totalPages &&
            !isLoading &&
            dy > 0 &&
            totalItemCount <= lastVisibleItemPosition.plus(2)) {
          showLoading()
          currentPage++
          viewModel.loadMore(currentPage)
        }
      }
    })

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
  }

  override fun onFailure(error: Throwable) {
    Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
    currentPage--
    hideLoading()
  }

  override fun onPagination(response: HomeResponse) {
    currentPage = response.page
    hideLoading()
    adapter.loadMore(response.results.filterNotNull().toMutableList())
  }

  private fun showLoading() {
    adapter.showLoading()
    isLoading = true
  }

  private fun hideLoading() {
    adapter.hideLoading()
    isLoading = false
  }
}