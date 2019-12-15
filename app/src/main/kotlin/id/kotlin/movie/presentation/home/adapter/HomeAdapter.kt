package id.kotlin.movie.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.load
import id.kotlin.movie.BuildConfig
import id.kotlin.movie.R
import id.kotlin.movie.data.home.HomeResponse.Result
import id.kotlin.movie.presentation.home.HomeViewState
import id.kotlin.movie.presentation.home.HomeViewState.Page
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.LOADING
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.RESULT
import kotlinx.android.synthetic.main.item_home.view.*
import kotlinx.android.synthetic.main.item_loading.view.*

class HomeAdapter(
    diffCallback: ItemCallback<Result> = HomeItemCallback,
    private val callback: HomeAdapterCallback
) : PagedListAdapter<Result, ViewHolder>(diffCallback) {

  lateinit var state: HomeViewState

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      when (viewType) {
        RESULT.ordinal -> {
          HomeViewHolder(
              LayoutInflater
                  .from(parent.context)
                  .inflate(
                      R.layout.item_home,
                      parent,
                      false
                  )
          )
        }
        LOADING.ordinal -> {
          LoadingViewHolder(
              LayoutInflater
                  .from(parent.context)
                  .inflate(
                      R.layout.item_loading,
                      parent,
                      false
                  )
          )
        }
        else -> throw RuntimeException("Illegal view type")
      }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    when (holder) {
      is HomeViewHolder -> getItem(holder.adapterPosition)?.let { holder.bind(it) }
      is LoadingViewHolder -> holder.bind(state)
    }
  }

  override fun getItemViewType(position: Int) =
      when (position) {
        itemCount.minus(1) -> LOADING.ordinal
        else -> RESULT.ordinal
      }

  inner class HomeViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(result: Result) {
      with(itemView) {
        iv_poster.load("${BuildConfig.IMAGE_URL}/${result.posterPath}")
        tv_title.text = result.title
        tv_rate.text = "${result.voteAverage}"

        rootView.setOnClickListener { callback.onClick(result) }
      }
    }
  }

  inner class LoadingViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(state: HomeViewState) {
      with(itemView) {
        pb_loading.visibility = if (state is Page) VISIBLE else GONE
      }
    }
  }

  interface HomeAdapterCallback {

    fun onClick(result: Result)
  }
}