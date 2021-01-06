package com.marcossalto.movies.ui.detail

/**
 * Created by Marcos Salto on 04/01/2021.
 */
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.marcossalto.data.repository.MoviesRepository
import com.marcossalto.data.repository.RegionRepository
import com.marcossalto.movies.R
import com.marcossalto.movies.databinding.ActivityDetailBinding
import com.marcossalto.movies.model.AndroidPermissionChecker
import com.marcossalto.movies.model.PlayServicesLocationDataSource
import com.marcossalto.movies.model.database.RoomDataSource
import com.marcossalto.movies.model.server.MovieDbDataSource
import com.marcossalto.movies.ui.common.app
import com.marcossalto.movies.ui.common.getViewModel
import com.marcossalto.movies.ui.common.loadUrl
import com.marcossalto.usecases.FindMovieById
import com.marcossalto.usecases.ToggleMovieFavorite

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel {
            val moviesRepository = MoviesRepository(
                RoomDataSource(app.db),
                MovieDbDataSource(),
                RegionRepository(
                    PlayServicesLocationDataSource(app),
                    AndroidPermissionChecker(app)
                ),
                app.getString(R.string.api_key)
            )

            DetailViewModel(
                intent.getIntExtra(MOVIE, -1),
                FindMovieById(moviesRepository),
                ToggleMovieFavorite(moviesRepository)
            )
        }

        viewModel.model.observe(this, Observer(::updateUi))

        binding.movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(binding) {
        val movie = model.movie
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)

        val icon = if (movie.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        movieDetailFavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, icon))
    }
}