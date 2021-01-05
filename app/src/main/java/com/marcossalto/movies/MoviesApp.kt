package com.marcossalto.movies

/**
 * Created by Marcos Salto on 05/01/2021.
 */
import android.app.Application
import androidx.room.Room
import com.marcossalto.movies.model.database.MovieDatabase

class MoviesApp : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
}