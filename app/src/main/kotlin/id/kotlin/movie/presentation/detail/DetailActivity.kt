package id.kotlin.movie.presentation.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.databinding.ActivityDetailBinding
import id.kotlin.movie.presentation.detail.adapter.DetailAdapter
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel: DetailView by lazy {
    ViewModelProvider(
        this,
        viewModelFactory
    )[DetailViewModel::class.java]
  }

  private lateinit var binding: ActivityDetailBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbarDetail)
    supportActionBar?.apply { setDisplayHomeAsUpEnabled(true) }

    val model = intent.getParcelableExtra<DetailModel>(DetailActivity::class.java.simpleName)
    viewModel.model.postValue(model)
    viewModel.model.observe(this, Observer { state ->
      binding.toolbarDetail.title = state.title
      binding.rvDetail.adapter = DetailAdapter(state)
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return super.onSupportNavigateUp()
  }
}