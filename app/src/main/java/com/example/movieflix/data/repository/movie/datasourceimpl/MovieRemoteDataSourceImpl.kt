package com.example.movieflix.data.repository.movie.datasourceimpl

import android.content.Context
import com.example.movieflix.data.api.BaseRemoteDataSource
import com.example.movieflix.data.api.MovieService
import com.example.movieflix.data.repository.movie.datasource.MovieRemoteDataSource

class MovieRemoteDataSourceImpl(
    private val movieService: MovieService,
    context: Context
    ): MovieRemoteDataSource, BaseRemoteDataSource(context) {
    override suspend fun getPopularMoviesFromApi() =
        getResult { movieService.getPopularMovies() }

    override suspend fun getTopRatedMoviesFromApi() =
        getResult { movieService.getTopRatedMovies() }

    override suspend fun searchMoviesByName(name: String) =
        getResult { movieService.searchMovieByName(name) }

    override suspend fun getVideosFromMovie(movieId: Int) =
        getResult { movieService.getVideosFromMovie(movieId) }
}