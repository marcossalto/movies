package com.marcossalto.usecases

import com.marcossalto.data.repository.MoviesRepository
import com.marcossalto.domain.Movie

/**
 * Created by Marcos Salto on 06/01/2021.
 */
class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()
}