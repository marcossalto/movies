package com.marcossalto.movies.ui.main

/**
 * Created by Marcos Salto on 04/01/2021.
 */

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.marcossalto.data.repository.MoviesRepository
import com.marcossalto.data.repository.RegionRepository
import com.marcossalto.movies.R
import com.marcossalto.movies.data.AndroidPermissionChecker
import com.marcossalto.movies.data.PlayServicesLocationDataSource
import com.marcossalto.movies.data.database.RoomDataSource
import com.marcossalto.movies.data.server.MovieDbDataSource
import com.marcossalto.movies.databinding.ActivityMainBinding
import com.marcossalto.movies.ui.common.PermissionRequester
import com.marcossalto.movies.ui.common.app
import com.marcossalto.movies.ui.common.getViewModel
import com.marcossalto.movies.ui.common.startActivity
import com.marcossalto.movies.ui.detail.DetailActivity
import com.marcossalto.movies.ui.main.MainViewModel.UiModel
import com.marcossalto.usecases.GetPopularMovies

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel {
            MainViewModel(
                GetPopularMovies(
                    MoviesRepository(
                        RoomDataSource(app.db),
                        MovieDbDataSource(),
                        RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        ),
                        app.getString(R.string.api_key)
                    )
                )
            )
        }

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {

        binding.progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is UiModel.Content -> adapter.movies = model.movies
            is UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie.id)
            }
            UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }
}