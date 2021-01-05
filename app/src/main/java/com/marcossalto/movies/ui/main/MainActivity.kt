package com.marcossalto.movies.ui.main

/**
 * Created by Marcos Salto on 04/01/2021.
 */

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.marcossalto.movies.databinding.ActivityMainBinding
import com.marcossalto.movies.model.MoviesRepository
import com.marcossalto.movies.ui.common.startActivity
import com.marcossalto.movies.ui.detail.DetailActivity
import com.marcossalto.movies.ui.main.MainViewModel.UiModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MoviesRepository(this))
        ).get()

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {

        binding.progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is UiModel.Content -> adapter.movies = model.movies
            is UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie)
            }
        }
    }
}