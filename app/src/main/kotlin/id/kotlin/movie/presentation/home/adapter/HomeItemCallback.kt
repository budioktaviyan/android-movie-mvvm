package id.kotlin.movie.presentation.home.adapter

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import id.kotlin.movie.data.home.HomeResponse.Result

object HomeItemCallback : ItemCallback<Result>() {

  override fun areItemsTheSame(oldItem: Result, newItem: Result) =
      oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: Result, newItem: Result) =
      oldItem == newItem
}