package id.kotlin.movie.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.kotlin.movie.R
import id.kotlin.movie.data.Result
import id.kotlin.movie.databinding.ItemHomeBinding
import id.kotlin.movie.presentation.home.adapter.HomeAdapter.HomeViewHolder

class HomeAdapter(private val results: List<Result>) : Adapter<HomeViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
      HomeViewHolder(
          DataBindingUtil.inflate(
              LayoutInflater.from(parent.context),
              R.layout.item_home,
              parent,
              false
          )
      )

  override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
    holder.binding.apply {
      viewModel = HomeViewModel(results[holder.adapterPosition])
      executePendingBindings()
    }
  }

  override fun getItemCount(): Int = results.count()

  inner class HomeViewHolder(val binding: ItemHomeBinding) : ViewHolder(binding.root)
}