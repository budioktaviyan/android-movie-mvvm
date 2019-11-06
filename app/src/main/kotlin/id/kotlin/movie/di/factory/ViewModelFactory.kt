package id.kotlin.movie.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    var viewModel: Provider<ViewModel>? = viewModels[modelClass]

    if (viewModel == null) {
      for ((key, value) in viewModels) {
        if (modelClass.isAssignableFrom(key)) {
          viewModel = value
          break
        }
      }
    }

    requireNotNull(viewModel) { "unknown model class $modelClass" }

    try {
      return viewModel.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }
}