package id.kotlin.movie.presentation.detail.adapter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import id.kotlin.movie.data.detail.DetailModel

class DetailBodyViewModel(model: DetailModel) : BaseObservable() {

  val overview: String = model.overview ?: "No Description"
    @Bindable get
}