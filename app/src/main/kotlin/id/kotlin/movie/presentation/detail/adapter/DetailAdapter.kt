package id.kotlin.movie.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.kotlin.movie.R
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
              DataBindingUtil.inflate(
                  LayoutInflater.from(parent.context),
                  R.layout.item_header,
                  parent,
                  false
              )
          )
        }
        BODY.ordinal -> {
          DetailBodyViewHolder(
              DataBindingUtil.inflate(
                  LayoutInflater.from(parent.context),
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
      is DetailHeaderViewHolder -> holder.binding.apply {
        viewModel = DetailHeaderViewModel(model)
        executePendingBindings()
      }
      is DetailBodyViewHolder -> holder.binding.apply {
        viewModel = DetailBodyViewModel(model)
        executePendingBindings()
      }
    }
  }

  override fun getItemCount(): Int = DetailAdapterType.values().count()

  override fun getItemViewType(position: Int): Int =
      when (position) {
        0 -> HEADER.ordinal
        else -> BODY.ordinal
      }

  inner class DetailHeaderViewHolder(val binding: ItemHeaderBinding) : ViewHolder(binding.root)
  inner class DetailBodyViewHolder(val binding: ItemBodyBinding) : ViewHolder(binding.root)
}