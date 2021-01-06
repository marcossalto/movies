package com.marcossalto.movies.ui.detail

/**
 * Created by Marcos Salto on 05/01/2021.
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marcossalto.domain.Movie
import com.marcossalto.movies.ui.common.ScopedViewModel
import com.marcossalto.usecases.FindMovieById
import com.marcossalto.usecases.ToggleMovieFavorite
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite
) :
    ScopedViewModel() {

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() = launch {
        _model.value = UiModel(findMovieById.invoke(movieId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.movie?.let {
            _model.value = UiModel(toggleMovieFavorite.invoke(it))
        }
    }
}