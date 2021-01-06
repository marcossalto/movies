package com.marcossalto.data.source

/**
 * Created by Marcos Salto on 06/01/2021.
 */
import com.marcossalto.domain.Movie

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun getPopularMovies(): List<Movie>
    suspend fun findById(id: Int): Movie
    suspend fun update(movie: Movie)
}