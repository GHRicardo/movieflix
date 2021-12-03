package com.example.movieflix.data.repository.movie

import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.data.repository.movie.datasource.MovieLocalDataSource
import com.example.movieflix.data.repository.movie.datasource.MovieRemoteDataSource
import com.example.movieflix.domain.repository.MovieRepository
import com.example.movieflix.other.Constants
import com.example.movieflix.other.Resource
import com.example.movieflix.other.Status
import kotlin.math.min

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
    ) : MovieRepository {
    override suspend fun getTopRatedMovies(): Resource<List<Movie>> {
        return getTopRatedMoviesFromDB()
    }

    private suspend fun getTopRatedMoviesFromDB():Resource<List<Movie>>{
        val movies = mutableListOf<Movie>()
        try {
            //movies.addAll(movieLocalDataSource.getTopRatedMoviesFromDB())
            //if(movies.isEmpty()){
                val movieResourceFromApi = getTopRatedMoviesFromApi()
                if(movieResourceFromApi.status == Status.SUCCESS){
                    movieResourceFromApi.data?.let{ movieList ->
                        movies.addAll(movieList)
                        movieLocalDataSource.deleteTopRatedMovies()
                        movieLocalDataSource.insertMoviesToDB(
                            movieList.map { it.apply {
                                type = Constants.TYPE_TOP_RATED
                            } })
                    }
                }
            movies.addAll(movieLocalDataSource.getTopRatedMoviesFromDB())
                //return movieResourceFromApi
            //}
            return Resource.success(movies)
        }catch (exception:Exception){
            val exceptionMessage= exception.message.toString()
            return Resource.error(exceptionMessage)
        }
    }

    private suspend fun getTopRatedMoviesFromApi():Resource<List<Movie>>{
        val result = movieRemoteDataSource.getTopRatedMoviesFromApi()
        if(result.status == Status.SUCCESS){
            result.data?.let{ movieResult ->
                return Resource.success(movieResult.movies)
            }
        }
        return Resource.error(result.message?:"Unexpected error")
    }

    override suspend fun getPopularMovies(): Resource<List<Movie>> {
        return getPopularMoviesFromDB()
    }

    private suspend fun getPopularMoviesFromDB():Resource<List<Movie>>{
        val movies = mutableListOf<Movie>()
        try {
            //movies.addAll(movieLocalDataSource.getPopularMoviesFromDB())
            //if(movies.isEmpty()){
                val movieResourceFromApi = getPopularMoviesFromApi()
                if(movieResourceFromApi.status == Status.SUCCESS){
                    movieResourceFromApi.data?.let{ movieList ->
                        movies.addAll(movieList)
                        movieLocalDataSource.deletePopularMovies()
                        movieLocalDataSource.insertMoviesToDB(
                            movieList.map { it.apply {
                                type = Constants.TYPE_POPULAR
                            } })
                    }
                }
            movies.addAll(movieLocalDataSource.getPopularMoviesFromDB())
                //return movieResourceFromApi
            //}
            return Resource.success(movies)
        }catch (exception:Exception){
            val exceptionMessage= exception.message.toString()
            return Resource.error(exceptionMessage)
        }
    }

    private suspend fun getPopularMoviesFromApi():Resource<List<Movie>>{
        val result = movieRemoteDataSource.getPopularMoviesFromApi()
        if(result.status == Status.SUCCESS){
            result.data?.let{ movieResult ->
                return Resource.success(movieResult.movies)
            }
        }
        return Resource.error(result.message?:"Unexpected error")
    }

    override suspend fun searchMovieByName(name: String): Resource<List<Movie>> {
        val result = movieRemoteDataSource.searchMoviesByName(name)
        if(result.status == Status.SUCCESS){
            result.data?.let{ movieResult ->
                return Resource.success(movieResult.movies)
            }
        }
        return Resource.error(result.message?:"Unexpected error")
    }

    override suspend fun getVideosFromMovie(videoId: Int): Resource<List<Video>> {
        val result = movieRemoteDataSource.getVideosFromMovie(videoId)
        if(result.status == Status.SUCCESS){
            result.data?.let{ videoResult ->
                return Resource.success(videoResult.videos)
            }
        }
        return Resource.error(result.message?:"Unexpected error")
    }

    override suspend fun getSomeRandomMovie(): Resource<Movie> {
        val movies = mutableListOf<Movie>()
        try {
            val randomNumber = (0..1).random()
            if(randomNumber == 0){
                movies.addAll(movieLocalDataSource.getPopularMoviesFromDB())
                if(movies.isEmpty()){
                    movies.addAll(movieLocalDataSource.getTopRatedMoviesFromDB())
                }
            }else{
                movies.addAll(movieLocalDataSource.getTopRatedMoviesFromDB())
                if(movies.isEmpty()){
                    movies.addAll(movieLocalDataSource.getPopularMoviesFromDB())
                }
            }
            if(movies.isEmpty()){
                if(randomNumber == 0){
                    val movieResourceFromApi = getPopularMoviesFromApi()
                    if(movieResourceFromApi.status == Status.SUCCESS){
                        movieResourceFromApi.data?.let{ movieList ->
                            movies.addAll(movieList)
                            movieLocalDataSource.deletePopularMovies()
                            movieLocalDataSource.insertMoviesToDB(
                                movieList.map { it.apply {
                                    type = Constants.TYPE_POPULAR
                                } })
                        }
                    }
                }else{
                    val movieResourceFromApi = getTopRatedMoviesFromApi()
                    if(movieResourceFromApi.status == Status.SUCCESS){
                        movieResourceFromApi.data?.let{ movieList ->
                            movies.addAll(movieList)
                            movieLocalDataSource.deleteTopRatedMovies()
                            movieLocalDataSource.insertMoviesToDB(
                                movieList.map { it.apply {
                                    type = Constants.TYPE_TOP_RATED
                                } })
                        }
                    }
                }
            }
            return if(movies.isNotEmpty()) {
                val limit = min(movies.size, 5)
                val index = (0 until limit).random()
                Resource.success(movies[index])
            }else{
                Resource.error("No movie to show")
            }
        }catch (exception:Exception){
            val exceptionMessage= exception.message.toString()
            return Resource.error(exceptionMessage)
        }
    }
}