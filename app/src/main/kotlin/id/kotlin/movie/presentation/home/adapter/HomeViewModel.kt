package id.kotlin.movie.presentation.home.adapter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import id.kotlin.movie.data.Result

class HomeViewModel(result: Result) : BaseObservable() {

  val title: String = result.title ?: "N/A"
    @Bindable get

  val rate: Double = result.voteAverage ?: 0.0
    @Bindable get
}