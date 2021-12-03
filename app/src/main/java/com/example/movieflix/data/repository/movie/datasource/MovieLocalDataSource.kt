package com.example.movieflix.data.repository.movie.datasource

import com.example.movieflix.data.model.movie.Movie

interface MovieLocalDataSource {
    suspend fun insertMoviesToDB(movies:List<Movie>)
    suspend fun getTopRatedMoviesFromDB():List<Movie>
    suspend fun getPopularMoviesFromDB():List<Movie>
    suspend fun deleteTopRatedMovies()
    suspend fun deletePopularMovies()
}