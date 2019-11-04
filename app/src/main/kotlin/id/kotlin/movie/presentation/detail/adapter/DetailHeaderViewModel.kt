package id.kotlin.movie.presentation.detail.adapter

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import coil.api.load
import id.kotlin.movie.BuildConfig
import id.kotlin.movie.data.detail.DetailModel

class DetailHeaderViewModel(model: DetailModel) : BaseObservable() {

  val backdrop: String = "${BuildConfig.IMAGE_URL}/${model.backdropPath ?: "untitled.jpg"}"
    @Bindable get
}

@BindingAdapter("backdrop")
fun ImageView.backdrop(url: String) {
  load(url)
}