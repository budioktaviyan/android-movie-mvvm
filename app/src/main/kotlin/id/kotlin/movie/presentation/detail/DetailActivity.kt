package id.kotlin.movie.presentation.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movie.R
import id.kotlin.movie.data.detail.DetailModel
import id.kotlin.movie.databinding.ActivityDetailBinding
import id.kotlin.movie.presentation.detail.adapter.DetailAdapter
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var viewModel: DetailViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val model = intent.getParcelableExtra(DetailActivity::class.java.simpleName) as DetailModel
    val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(
        this,
        R.layout.activity_detail)
        .apply { viewModel = this@DetailActivity.viewModel }
        .also {
          viewModel.model = model
          viewModel.setTitle()
        }

    setSupportActionBar(binding.toolbarDetail)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setHomeAsUpIndicator(
          ContextCompat.getDrawable(
              this@DetailActivity,
              R.drawable.ic_arrow_back
          )
      )
    }

    binding.rvDetail.adapter = DetailAdapter(model)
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return super.onSupportNavigateUp()
  }
}