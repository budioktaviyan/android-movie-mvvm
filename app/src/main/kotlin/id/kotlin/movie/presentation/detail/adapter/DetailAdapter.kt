package id.kotlin.movie.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.load
import id.kotlin.movie.BuildConfig
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.databinding.ItemBodyBinding
import id.kotlin.movie.databinding.ItemHeaderBinding
import id.kotlin.movie.presentation.detail.adapter.DetailAdapterType.BODY
import id.kotlin.movie.presentation.detail.adapter.DetailAdapterType.HEADER

class DetailAdapter(
    private val model: DetailModel
) : Adapter<ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
      when (viewType) {
        HEADER.ordinal -> {
          DetailHeaderViewHolder(
              ItemHeaderBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              )
          )
        }
        BODY.ordinal -> {
          DetailBodyViewHolder(
              ItemBodyBinding.inflate(
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

  inner class DetailHeaderViewHolder(private val binding: ItemHeaderBinding) : ViewHolder(binding.root) {

    fun bind(model: DetailModel) {
      binding.ivBackdrop.load("${BuildConfig.IMAGE_URL}/${model.backdropPath}")
    }
  }

  inner class DetailBodyViewHolder(private val binding: ItemBodyBinding) : ViewHolder(binding.root) {

    fun bind(model: DetailModel) {
      binding.tvOverview.text = model.overview
    }
  }
}