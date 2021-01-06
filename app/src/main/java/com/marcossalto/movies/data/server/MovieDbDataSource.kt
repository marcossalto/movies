package com.marcossalto.movies.data.server

/**
 * Created by Marcos Salto on 06/01/2021.
 */
import com.marcossalto.data.source.RemoteDataSource
import com.marcossalto.domain.Movie
import com.marcossalto.movies.data.toDomainMovie

class MovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        MovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}