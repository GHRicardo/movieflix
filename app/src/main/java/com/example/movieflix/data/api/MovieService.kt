package com.example.movieflix.data.api

import com.example.movieflix.BuildConfig
import com.example.movieflix.data.model.movie.MovieResult
import com.example.movieflix.data.model.series.SerieResult
import com.example.movieflix.data.model.video.VideoResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey:String = BuildConfig.API_KEY
    ):Response<MovieResult>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ):Response<MovieResult>

    @GET("search/movie")
    suspend fun searchMovieByName(
        @Query("query") name:String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ):Response<MovieResult>

    @GET("movie/{id}/videos")
    suspend fun getVideosFromMovie(
        @Path("id") movieId:Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ):Response<VideoResult>

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") apiKey:String = BuildConfig.API_KEY
    ):Response<SerieResult>

    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ):Response<SerieResult>

    @GET("search/tv")
    suspend fun searchSerieByName(
        @Query("query") name:String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ):Response<SerieResult>

    @GET("tv/{id}/videos")
    suspend fun getVideosFromSerie(
        @Path("id") serieId:Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ):Response<VideoResult>
}