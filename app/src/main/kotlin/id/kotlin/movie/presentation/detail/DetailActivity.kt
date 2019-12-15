package id.kotlin.movie.presentation.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.presentation.detail.adapter.DetailAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel: DetailView by lazy {
    ViewModelProviders
        .of(
            this,
            viewModelFactory
        )[DetailViewModel::class.java]
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)
    setSupportActionBar(toolbar_detail)
    supportActionBar?.apply { setDisplayHomeAsUpEnabled(true) }

    val model = intent.getParcelableExtra<DetailModel>(DetailActivity::class.java.simpleName)
    viewModel.model.postValue(model)
    viewModel.model.observe(this, Observer { state ->
      toolbar_detail.title = state.title
      rv_detail.adapter = DetailAdapter(state)
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return super.onSupportNavigateUp()
  }
}