package id.kotlin.movie.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.load
import id.kotlin.movie.BuildConfig
import id.kotlin.movie.data.home.HomeResponse.Result
import id.kotlin.movie.databinding.ItemHomeBinding
import id.kotlin.movie.databinding.ItemLoadingBinding
import id.kotlin.movie.presentation.home.HomeViewState
import id.kotlin.movie.presentation.home.HomeViewState.Page
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.LOADING
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.RESULT

class HomeAdapter(
    diffCallback: ItemCallback<Result> = HomeItemCallback,
    private val callback: HomeAdapterCallback
) : PagedListAdapter<Result, ViewHolder>(diffCallback) {

  lateinit var state: HomeViewState

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      when (viewType) {
        RESULT.ordinal -> {
          HomeViewHolder(
              ItemHomeBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              )
          )
        }
        LOADING.ordinal -> {
          LoadingViewHolder(
              ItemLoadingBinding.inflate(
                  LayoutInflater.from(parent.context),
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

  inner class HomeViewHolder(private val binding: ItemHomeBinding) : ViewHolder(binding.root) {

    fun bind(result: Result) {
      binding.ivPoster.load("${BuildConfig.IMAGE_URL}/${result.posterPath}")
      binding.tvTitle.text = result.title
      binding.tvRate.text = "${result.voteAverage}"

      binding.root.setOnClickListener { callback.onClick(result) }
    }
  }

  inner class LoadingViewHolder(private val binding: ItemLoadingBinding) : ViewHolder(binding.root) {

    fun bind(state: HomeViewState) {
      binding.pbLoading.visibility = if (state is Page) VISIBLE else GONE
    }
  }

  interface HomeAdapterCallback {

    fun onClick(result: Result)
  }
}