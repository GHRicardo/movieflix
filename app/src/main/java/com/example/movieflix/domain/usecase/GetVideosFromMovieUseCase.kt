package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.MovieRepository

class GetVideosFromMovieUseCase(private val movieRepository: MovieRepository) {
    suspend fun invoke(movieID:Int) = movieRepository.getVideosFromMovie(movieID)
}