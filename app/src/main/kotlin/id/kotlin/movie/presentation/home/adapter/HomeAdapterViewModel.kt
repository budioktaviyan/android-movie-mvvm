package id.kotlin.movie.presentation.home.adapter

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import coil.api.load
import id.kotlin.movie.BuildConfig
import id.kotlin.movie.data.Result

class HomeAdapterViewModel(result: Result?) : BaseObservable() {

  val title: String = result?.title ?: "Untitled"
    @Bindable get

  val rate: Double = result?.voteAverage ?: 0.0
    @Bindable get

  val poster: String = "${BuildConfig.IMAGE_URL}/${result?.posterPath ?: "untitled.jpg"}"
    @Bindable get
}

@BindingAdapter("poster")
fun ImageView.poster(url: String) {
  load(url)
}