package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.MovieRepository

class GetSomeRandomMovieUseCase(private val movieRepository: MovieRepository) {
    suspend fun invoke() = movieRepository.getSomeRandomMovie()
}