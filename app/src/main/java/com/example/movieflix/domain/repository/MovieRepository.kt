package com.example.movieflix.domain.repository

import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.other.Resource

interface MovieRepository {

    suspend fun getTopRatedMovies(): Resource<List<Movie>>

    suspend fun getPopularMovies(): Resource<List<Movie>>

    suspend fun searchMovieByName(name:String): Resource<List<Movie>>

    suspend fun getSomeRandomMovie():Resource<Movie>

    suspend fun getVideosFromMovie(videoId:Int):Resource<List<Video>>
}