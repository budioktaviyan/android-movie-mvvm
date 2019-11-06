package id.kotlin.movie.presentation.home.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.load
import id.kotlin.movie.BuildConfig
import id.kotlin.movie.R
import id.kotlin.movie.data.home.Result
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.LOADING
import id.kotlin.movie.presentation.home.adapter.HomeAdapterType.RESULT
import kotlinx.android.synthetic.main.item_home.view.*

class HomeAdapter(
    private var results: MutableList<Result?>,
    private val callback: HomeAdapterCallback
) : Adapter<ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
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
      is HomeViewHolder -> holder.bind(results[holder.adapterPosition])
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

  inner class HomeViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(result: Result?) {
      with(itemView) {
        iv_poster.load("${BuildConfig.IMAGE_URL}/${result?.posterPath ?: "untitled.jpg"}")
        tv_title.text = result?.title ?: "Untitled"
        tv_rate.text = "${result?.voteAverage ?: 0.0}"

        rootView.setOnClickListener { result?.let { callback.onClick(it) } }
      }
    }
  }

  inner class LoadingViewHolder(itemView: View) : ViewHolder(itemView)

  interface HomeAdapterCallback {

    fun onClick(result: Result)
  }
}