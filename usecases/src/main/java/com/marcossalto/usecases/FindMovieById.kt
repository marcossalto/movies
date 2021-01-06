package com.marcossalto.usecases

import com.marcossalto.data.repository.MoviesRepository
import com.marcossalto.domain.Movie

/**
 * Created by Marcos Salto on 06/01/2021.
 */
class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}