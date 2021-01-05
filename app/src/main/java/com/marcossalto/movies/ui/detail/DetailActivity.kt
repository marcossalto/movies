package com.marcossalto.movies.ui.detail

/**
 * Created by Marcos Salto on 04/01/2021.
 */
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcossalto.movies.databinding.ActivityDetailBinding
import com.marcossalto.movies.model.Movie
import com.marcossalto.movies.ui.common.loadUrl

class DetailActivity : AppCompatActivity(), DetailPresenter.View {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val presenter = DetailPresenter()
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movie: Movie = intent.getParcelableExtra(MOVIE)
            ?: throw (IllegalStateException("Movie not found"))

        presenter.onCreate(this@DetailActivity, movie)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateUI(movie: Movie) = with(binding) {
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}