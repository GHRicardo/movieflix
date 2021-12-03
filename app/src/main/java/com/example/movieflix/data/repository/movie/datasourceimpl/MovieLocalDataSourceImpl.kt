package com.example.movieflix.data.repository.movie.datasourceimpl

import com.example.movieflix.data.db.MovieDao
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.repository.movie.datasource.MovieLocalDataSource
import com.example.movieflix.other.Constants

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {
    override suspend fun insertMoviesToDB(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }

    override suspend fun getTopRatedMoviesFromDB(): List<Movie> {
        return movieDao.getMoviesByType(Constants.TYPE_TOP_RATED)
    }

    override suspend fun getPopularMoviesFromDB(): List<Movie> {
        return movieDao.getMoviesByType(Constants.TYPE_POPULAR)
    }

    override suspend fun deleteTopRatedMovies() {
        movieDao.deleteMoviesByType(Constants.TYPE_TOP_RATED)
    }

    override suspend fun deletePopularMovies() {
        movieDao.deleteMoviesByType(Constants.TYPE_POPULAR)
    }
}