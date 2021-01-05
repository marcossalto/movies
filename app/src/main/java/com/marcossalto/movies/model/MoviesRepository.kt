package com.marcossalto.movies.model

/**
 * Created by Marcos Salto on 04/01/2021.
 */
import android.app.Application
import com.marcossalto.movies.R

class MoviesRepository(application: Application) {

    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository = RegionRepository(application)

    suspend fun findPopularMovies() =
        MovieDb.service
            .listPopularMoviesAsync(
                apiKey,
                regionRepository.findLastRegion()
            )
}