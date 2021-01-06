package com.marcossalto.data.repository

/**
 * Created by Marcos Salto on 06/01/2021.
 */
import com.marcossalto.data.source.LocalDataSource
import com.marcossalto.data.source.RemoteDataSource
import com.marcossalto.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {

    suspend fun getPopularMovies(): List<Movie> {

        if (localDataSource.isEmpty()) {
            val movies =
                remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)
}