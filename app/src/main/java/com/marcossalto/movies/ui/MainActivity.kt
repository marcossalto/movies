package com.marcossalto.movies.ui

/**
 * Created by Marcos Salto on 04/01/2021.
 */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcossalto.movies.MovieDb
import com.marcossalto.movies.R
import com.marcossalto.movies.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val adapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter

        GlobalScope.launch(Dispatchers.Main) {
            val movies = MovieDb.service.listPopularMoviesAsync(getString(R.string.api_key))
            adapter.movies = movies.results
        }
    }
}