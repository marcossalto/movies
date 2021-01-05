package com.marcossalto.movies.ui.detail

/**
 * Created by Marcos Salto on 05/01/2021.
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marcossalto.movies.model.Movie

class DetailViewModel(private val movie: Movie) : ViewModel() {

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) _model.value = UiModel(movie)
            return _model
        }
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val movie: Movie) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailViewModel(movie) as T
}