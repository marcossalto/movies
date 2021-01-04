package com.marcossalto.movies.model

/**
 * Created by Marcos Salto on 04/01/2021.
 */
import android.app.Activity
import com.marcossalto.movies.R

class MoviesRepository(activity: Activity) {

    private val apiKey = activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)

    suspend fun findPopularMovies() =
        MovieDb.service
            .listPopularMoviesAsync(
                apiKey,
                regionRepository.findLastRegion()
            )
}