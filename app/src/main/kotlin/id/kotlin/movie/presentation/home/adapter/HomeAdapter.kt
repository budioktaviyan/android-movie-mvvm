package id.kotlin.movie.presentation.home.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.kotlin.movie.R
import id.kotlin.movie.data.home.Result
import id.kotlin.movie.databinding.ItemHomeBinding
import id.kotlin.movie.databinding.ItemLoadingBinding
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.LOADING
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.RESULT

class HomeAdapter(
    private var results: MutableList<Result?>,
    private val callback: HomeAdapterCallback
) : Adapter<ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
      when (viewType) {
        RESULT.ordinal -> {
          HomeViewHolder(
              DataBindingUtil.inflate(
                  LayoutInflater.from(parent.context),
                  R.layout.item_home,
                  parent,
                  false
              )
          )
        }
        LOADING.ordinal -> {
          LoadingViewHolder(
              DataBindingUtil.inflate(
                  LayoutInflater.from(parent.context),
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
      is HomeViewHolder -> holder.binding.apply {
        viewModel = HomeAdapterViewModel(results[holder.adapterPosition], callback)
        executePendingBindings()
      }
      is LoadingViewHolder -> holder.binding.apply { executePendingBindings() }
    }
  }

  override fun getItemCount(): Int = results.count()

  override fun getItemViewType(position: Int): Int =
      when {
        results[position] == null -> LOADING.ordinal
        else -> RESULT.ordinal
      }

  fun showLoading() {
    results.add(null)
    Handler().post { notifyItemInserted(results.count().minus(1)) }
  }

  fun hideLoading() {
    results.removeAt(results.count().minus(1))
    Handler().post { notifyItemRemoved(results.count()) }
  }

  fun loadMore(results: MutableList<Result?>) {
    this.results.addAll(results)
    Handler().post { notifyDataSetChanged() }
  }

  fun reload(results: MutableList<Result?>) {
    this.results.clear()
    this.results = results
    Handler().post { notifyDataSetChanged() }
  }

  inner class HomeViewHolder(val binding: ItemHomeBinding) : ViewHolder(binding.root)
  inner class LoadingViewHolder(val binding: ItemLoadingBinding) : ViewHolder(binding.root)

  interface HomeAdapterCallback {

    fun onClick(result: Result)
  }
}