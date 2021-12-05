package com.example.movieflix.data.repository.movie

import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.domain.repository.MovieRepository
import com.example.movieflix.other.Constants.TYPE_POPULAR
import com.example.movieflix.other.Constants.TYPE_TOP_RATED
import com.example.movieflix.other.Resource

class FakeMovieRepositoryAndroidTestImpl(
    private val movies: List<Movie>,
    private val videosByMovieId: Map<Int, List<Video>>
) : MovieRepository {

    override suspend fun getTopRatedMovies(): Resource<List<Movie>> {
        return Resource.success(movies.filter { it.type == TYPE_TOP_RATED })
    }

    override suspend fun getPopularMovies(): Resource<List<Movie>> {
        return Resource.success(movies.filter { it.type == TYPE_POPULAR })
    }

    override suspend fun searchMovieByName(name: String): Resource<List<Movie>> {
        return Resource.success(movies.filter { it.title == name })
    }

    override suspend fun getSomeRandomMovie(): Resource<Movie> {
        val index = (movies.indices).random()
        return Resource.success(movies[index])
    }

    override suspend fun getVideosFromMovie(videoId: Int): Resource<List<Video>> {
        return Resource.success(videosByMovieId[videoId])
    }
}