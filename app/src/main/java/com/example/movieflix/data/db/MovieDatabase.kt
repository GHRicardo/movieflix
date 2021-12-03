package com.example.movieflix.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieflix.data.model.movie.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao():MovieDao
}