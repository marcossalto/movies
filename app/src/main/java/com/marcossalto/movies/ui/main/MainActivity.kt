package com.marcossalto.movies.ui.main

/**
 * Created by Marcos Salto on 04/01/2021.
 */

import android.os.Bundle
import android.view.View
import com.marcossalto.movies.databinding.ActivityMainBinding
import com.marcossalto.movies.model.MoviesRepository
import com.marcossalto.movies.ui.common.CoroutineScopeActivity
import com.marcossalto.movies.ui.common.startActivity
import com.marcossalto.movies.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity() {

    private val moviesRepository: MoviesRepository by lazy { MoviesRepository(this) }

    private val adapter = MoviesAdapter {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityMainBinding.inflate(layoutInflater)) {
            setContentView(root)

            recycler.adapter = adapter

            launch {
                progress.visibility = View.VISIBLE
                adapter.movies = moviesRepository.findPopularMovies().results
                progress.visibility = View.GONE
            }
        }
    }
}