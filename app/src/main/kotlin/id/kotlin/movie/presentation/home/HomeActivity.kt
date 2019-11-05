package id.kotlin.movie.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.data.home.HomeResponse
import id.kotlin.movie.data.home.Result
import id.kotlin.movie.databinding.ActivityHomeBinding
import id.kotlin.movie.presentation.detail.DetailActivity
import id.kotlin.movie.presentation.home.adapter.HomeAdapter
import id.kotlin.movie.presentation.home.adapter.HomeAdapter.HomeAdapterCallback
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.LOADING
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.RESULT
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), HomeViewModelCallback, HomeAdapterCallback {

  @Inject
  lateinit var viewModel: HomeViewModel

  private lateinit var binding: ActivityHomeBinding

  private var adapter: HomeAdapter? = null
  private var isLoading: Boolean = false
  private var currentPage: Long = -1L

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = DataBindingUtil.setContentView<ActivityHomeBinding>(
        this,
        R.layout.activity_home)
        .apply { viewModel = this@HomeActivity.viewModel }
        .also { viewModel.discoverMovie() }

    binding.srlHome.setColorSchemeColors(
        ContextCompat.getColor(
            this,
            R.color.black
        )
    )

    binding.srlHome.setOnRefreshListener { viewModel.reload() }
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.onDetach()
  }

  override fun onResponse(response: HomeResponse) {
    adapter = HomeAdapter(response.results.filterNotNull().toMutableList(), this)
    binding.rvHome.adapter = adapter
    binding.rvHome.setHasFixedSize(true)

    currentPage = response.page
    binding.rvHome.addOnScrollListener(object : OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (currentPage >= response.totalPages || isLoading) return

        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount

        if ((lastVisibleItemPosition.plus(2)) > totalItemCount) {
          showLoading()
          currentPage++
          viewModel.loadMore(currentPage)
        }
      }
    })

    (binding.rvHome.layoutManager as GridLayoutManager).apply {
      spanSizeLookup = object : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int =
            when (adapter?.getItemViewType(position)) {
              RESULT.ordinal -> 1
              LOADING.ordinal -> 2
              else -> throw RuntimeException("Illegal view type")
            }
      }
    }
  }

  override fun onFailure(error: Throwable) {
    Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
  }

  override fun onPaginationSuccess(response: HomeResponse) {
    currentPage = response.page
    hideLoading()
    adapter?.loadMore(response.results.filterNotNull().toMutableList())
  }

  override fun onPaginationError(error: Throwable) {
    Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
    currentPage--
    hideLoading()
  }

  override fun onReloadSuccess(response: HomeResponse) {
    currentPage = response.page
    binding.srlHome.isRefreshing = false
    adapter?.reload(response.results.filterNotNull().toMutableList())
  }

  override fun onReloadError(error: Throwable) {
    Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
    binding.srlHome.isRefreshing = false
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
                  title = result.title,
                  overview = result.overview,
                  backdropPath = result.backdropPath
              )
          )
        }
    )
  }

  private fun showLoading() {
    adapter?.showLoading()
    isLoading = true
  }

  private fun hideLoading() {
    adapter?.hideLoading()
    isLoading = false
  }
}