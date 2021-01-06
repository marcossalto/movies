package com.marcossalto.data.source

/**
 * Created by Marcos Salto on 06/01/2021.
 */
import com.marcossalto.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}