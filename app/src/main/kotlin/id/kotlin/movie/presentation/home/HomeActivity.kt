package id.kotlin.movie.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.data.home.Result
import id.kotlin.movie.presentation.detail.DetailActivity
import id.kotlin.movie.presentation.home.HomeViewState.Error
import id.kotlin.movie.presentation.home.HomeViewState.Loading
import id.kotlin.movie.presentation.home.HomeViewState.PaginationError
import id.kotlin.movie.presentation.home.HomeViewState.PaginationSuccess
import id.kotlin.movie.presentation.home.HomeViewState.ReloadError
import id.kotlin.movie.presentation.home.HomeViewState.ReloadSuccess
import id.kotlin.movie.presentation.home.HomeViewState.Success
import id.kotlin.movie.presentation.home.adapter.HomeAdapter
import id.kotlin.movie.presentation.home.adapter.HomeAdapter.HomeAdapterCallback
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.LOADING
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.RESULT
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), HomeAdapterCallback {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel: HomeView by lazy {
    ViewModelProviders
        .of(this, viewModelFactory)
        .get(HomeViewModel::class.java)
  }

  private var adapter: HomeAdapter? = null
  private var isLoading: Boolean = false
  private var currentPage: Long = -1L

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    viewModel.states.observe(this, Observer { state ->
      when (state) {
        is Loading -> {
          pb_home.visibility = VISIBLE
        }
        is Success -> {
          pb_home.visibility = GONE

          adapter = HomeAdapter(
              state.response.results.filterNotNull().toMutableList(),
              this
          )
          rv_home.adapter = adapter
          rv_home.setHasFixedSize(true)

          currentPage = state.response.page
          rv_home.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
              super.onScrolled(recyclerView, dx, dy)
              if (currentPage >= state.response.totalPages || isLoading) return

              val layoutManager = recyclerView.layoutManager as GridLayoutManager
              val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
              val totalItemCount = layoutManager.itemCount

              if ((lastVisibleItemPosition.plus(2)) > totalItemCount) {
                adapter?.showLoading()
                isLoading = true
                currentPage++
                viewModel.loadMore(currentPage)
              }
            }
          })

          (rv_home.layoutManager as GridLayoutManager).apply {
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
        is Error -> {
          pb_home.visibility = GONE
          Log.e(HomeActivity::class.java.simpleName, "${state.error.printStackTrace()}")
        }
        is PaginationSuccess -> {
          currentPage = state.response.page
          adapter?.hideLoading()
          isLoading = false
          adapter?.loadMore(state.response.results.filterNotNull().toMutableList())
        }
        is PaginationError -> {
          Log.e(HomeActivity::class.java.simpleName, "${state.error.printStackTrace()}")
          currentPage--
          adapter?.hideLoading()
          isLoading = false
        }
        is ReloadSuccess -> {
          currentPage = state.response.page
          srl_home.isRefreshing = false
          adapter?.reload(state.response.results.filterNotNull().toMutableList())
        }
        is ReloadError -> {
          Log.e(HomeActivity::class.java.simpleName, "${state.error.printStackTrace()}")
          srl_home.isRefreshing = false
        }
      }
    })
    viewModel.discoverMovie()

    srl_home.setColorSchemeColors(
        ContextCompat.getColor(
            this,
            R.color.black
        )
    )
    srl_home.setOnRefreshListener { viewModel.reload() }
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
}