    package com.marcossalto.movies.ui.main

/**
 * Created by Marcos Salto on 05/01/2021.
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marcossalto.domain.Movie
import com.marcossalto.movies.ui.common.ScopedViewModel
import com.marcossalto.usecases.GetPopularMovies
import kotlinx.coroutines.launch

class MainViewModel(private val getPopularMovies: GetPopularMovies) : ScopedViewModel() {

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
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getPopularMovies.invoke())
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