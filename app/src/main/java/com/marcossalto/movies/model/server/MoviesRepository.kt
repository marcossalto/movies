package com.marcossalto.movies.model.server

/**
 * Created by Marcos Salto on 04/01/2021.
 */
import com.marcossalto.movies.MoviesApp
import com.marcossalto.movies.R
import com.marcossalto.movies.model.RegionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.marcossalto.movies.model.database.Movie as DbMovie
import com.marcossalto.movies.model.server.Movie as ServerMovie

class MoviesRepository(application: MoviesApp) {

    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository = RegionRepository(application)
    private val db = application.db

    suspend fun findPopularMovies(): List<DbMovie> = withContext(Dispatchers.IO) {

        with(db.movieDao()) {
            if (movieCount() <= 0) {

                val movies = MovieDb.service
                    .listPopularMoviesAsync(apiKey, regionRepository.findLastRegion())
                    .results

                insertMovies(movies.map(ServerMovie::convertToDbMovie))
            }

            getAll()
        }
    }

    suspend fun findById(id: Int): DbMovie = withContext(Dispatchers.IO) {
        db.movieDao().findById(id)
    }
}

private fun ServerMovie.convertToDbMovie() = DbMovie(
    0,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: posterPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    false
)