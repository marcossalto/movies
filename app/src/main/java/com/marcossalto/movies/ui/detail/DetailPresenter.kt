package com.marcossalto.movies.ui.detail

import com.marcossalto.movies.model.Movie

/**
 * Created by Marcos Salto on 04/01/2021.
 */
class DetailPresenter {

    private var view: View? = null

    interface View {
        fun updateUI(movie: Movie)
    }

    fun onCreate(view: View, movie: Movie) {
        this.view = view
        view.updateUI(movie)
    }

    fun onDestroy() {
        view = null
    }
}