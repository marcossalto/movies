package com.marcossalto.movies.ui.main

/**
 * Created by Marcos Salto on 05/01/2021.
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marcossalto.movies.model.Movie
import com.marcossalto.movies.model.MoviesRepository
import com.marcossalto.movies.ui.common.Scope
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel(),
    Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
        class Navigation(val movie: Movie) : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(moviesRepository.findPopularMovies().results)
        }
    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val moviesRepository: MoviesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(moviesRepository) as T
}