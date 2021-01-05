package com.marcossalto.movies.ui.main

/**
 * Created by Marcos Salto on 04/01/2021.
 */

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.marcossalto.movies.databinding.ActivityMainBinding
import com.marcossalto.movies.model.Movie
import com.marcossalto.movies.model.MoviesRepository
import com.marcossalto.movies.ui.common.startActivity
import com.marcossalto.movies.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter by lazy { MainPresenter(MoviesRepository(this)) }
    private val adapter = MoviesAdapter { presenter.onMovieClicked(it) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            presenter.onCreate(this@MainActivity)
            recycler.adapter = adapter
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun updateData(movies: List<Movie>) {
        adapter.movies = movies
    }

    override fun navigateTo(movie: Movie) {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, movie)
        }
    }
}