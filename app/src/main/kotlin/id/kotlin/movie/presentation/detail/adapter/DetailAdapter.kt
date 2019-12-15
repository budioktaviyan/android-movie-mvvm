package id.kotlin.movie.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.load
import id.kotlin.movie.BuildConfig
import id.kotlin.movie.R
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.presentation.detail.adapter.DetailAdapterType.BODY
import id.kotlin.movie.presentation.detail.adapter.DetailAdapterType.HEADER
import kotlinx.android.synthetic.main.item_body.view.*
import kotlinx.android.synthetic.main.item_header.view.*

class DetailAdapter(
    private val model: DetailModel
) : Adapter<ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
      when (viewType) {
        HEADER.ordinal -> {
          DetailHeaderViewHolder(
              LayoutInflater
                  .from(parent.context)
                  .inflate(
                      R.layout.item_header,
                      parent,
                      false
                  )
          )
        }
        BODY.ordinal -> {
          DetailBodyViewHolder(
              LayoutInflater
                  .from(parent.context)
                  .inflate(
                      R.layout.item_body,
                      parent,
                      false
                  )
          )
        }
        else -> throw RuntimeException("Illegal view type")
      }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    when (holder) {
      is DetailHeaderViewHolder -> holder.bind(model)
      is DetailBodyViewHolder -> holder.bind(model)
    }
  }

  override fun getItemCount(): Int = DetailAdapterType.values().count()

  override fun getItemViewType(position: Int): Int =
      when (position) {
        0 -> HEADER.ordinal
        else -> BODY.ordinal
      }

  inner class DetailHeaderViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(model: DetailModel) {
      with(itemView) { iv_backdrop.load("${BuildConfig.IMAGE_URL}/${model.backdropPath}") }
    }
  }

  inner class DetailBodyViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(model: DetailModel) {
      with(itemView) { tv_overview.text = model.overview }
    }
  }
}