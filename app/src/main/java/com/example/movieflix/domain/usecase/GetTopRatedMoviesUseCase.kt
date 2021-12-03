package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.MovieRepository

class GetTopRatedMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun invoke() = movieRepository.getTopRatedMovies()
}