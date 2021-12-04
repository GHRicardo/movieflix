package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.MovieRepository
import javax.inject.Inject

class GetVideosFromMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository) {
    suspend fun invoke(movieID:Int) = movieRepository.getVideosFromMovie(movieID)
}