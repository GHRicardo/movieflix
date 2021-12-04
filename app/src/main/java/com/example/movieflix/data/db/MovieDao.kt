package com.example.movieflix.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieflix.data.model.movie.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM movies WHERE type=:type")
    suspend fun deleteMoviesByType(type:String)

    @Query("SELECT * FROM movies WHERE type=:type")
    suspend fun getMoviesByType(type:String):List<Movie>

    @Query("SELECT * FROM movies")
    suspend fun getMovies():List<Movie>
}