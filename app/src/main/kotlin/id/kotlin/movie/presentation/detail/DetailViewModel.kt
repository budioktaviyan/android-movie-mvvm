package id.kotlin.movie.presentation.detail

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import id.kotlin.movie.data.detail.DetailModel

class DetailViewModel(
    var model: DetailModel
) : BaseObservable(), DetailView {

  var title: String = String()
    @Bindable get

  override fun setTitle() {
    title = model.title ?: "Untitled"
    notifyPropertyChanged(BR.title)
  }
}