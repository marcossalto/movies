package com.marcossalto.movies.ui.detail

/**
 * Created by Marcos Salto on 05/01/2021.
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marcossalto.movies.model.database.Movie
import com.marcossalto.movies.model.server.MoviesRepository
import com.marcossalto.movies.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class DetailViewModel(private val movieId: Int, private val moviesRepository: MoviesRepository) :
    ScopedViewModel() {

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() {
        launch {
            _model.value = UiModel(moviesRepository.findById(movieId))
        }
    }
}