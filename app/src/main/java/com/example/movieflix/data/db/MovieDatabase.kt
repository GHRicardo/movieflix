package com.example.movieflix.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.model.series.Serie

@Database(
    entities = [Movie::class, Serie::class],
    version = 4,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao():MovieDao
    abstract fun serieDao():SerieDao
}