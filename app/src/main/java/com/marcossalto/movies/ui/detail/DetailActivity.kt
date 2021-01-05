package com.marcossalto.movies.ui.detail

/**
 * Created by Marcos Salto on 04/01/2021.
 */
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.marcossalto.movies.databinding.ActivityDetailBinding
import com.marcossalto.movies.model.Movie
import com.marcossalto.movies.ui.common.loadUrl

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
        val movie: Movie = intent.getParcelableExtra(MOVIE)
            ?: throw (IllegalStateException("Movie not found"))

        viewModel = ViewModelProvider(
            this,
            DetailViewModelFactory(movie)
        ).get()

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(binding) {
        val movie = model.movie
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}