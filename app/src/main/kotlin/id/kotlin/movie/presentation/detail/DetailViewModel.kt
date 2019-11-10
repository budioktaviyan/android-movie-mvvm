package id.kotlin.movie.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.kotlin.movie.data.detail.DetailModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : ViewModel(), DetailView {

  override var model: MutableLiveData<DetailModel> = MutableLiveData()
}