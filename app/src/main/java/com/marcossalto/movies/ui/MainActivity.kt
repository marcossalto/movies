package com.marcossalto.movies.ui

/**
 * Created by Marcos Salto on 04/01/2021.
 */

import android.os.Bundle
import com.marcossalto.movies.MovieDb
import com.marcossalto.movies.R
import com.marcossalto.movies.databinding.ActivityMainBinding
import com.marcossalto.movies.ui.common.CoroutineScopeActivity
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity() {

    private val adapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter

        launch {
            val movies = MovieDb.service
                .listPopularMoviesAsync(getString(R.string.api_key))
            adapter.movies = movies.results
        }
    }
}