package com.example.movieflix.data.repository.movie.datasource

import com.example.movieflix.data.model.movie.MovieResult
import com.example.movieflix.data.model.video.VideoResult
import com.example.movieflix.other.Resource

interface MovieRemoteDataSource {
    suspend fun getPopularMoviesFromApi():Resource<MovieResult>
    suspend fun getTopRatedMoviesFromApi():Resource<MovieResult>
    suspend fun searchMoviesByName(name:String):Resource<MovieResult>
    suspend fun getVideosFromMovie(movieId:Int):Resource<VideoResult>
}