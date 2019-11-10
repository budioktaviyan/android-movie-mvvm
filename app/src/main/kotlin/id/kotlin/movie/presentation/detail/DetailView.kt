package id.kotlin.movie.presentation.detail

import androidx.lifecycle.MutableLiveData
import id.kotlin.movie.data.detail.DetailModel

interface DetailView {

  var model: MutableLiveData<DetailModel>
}