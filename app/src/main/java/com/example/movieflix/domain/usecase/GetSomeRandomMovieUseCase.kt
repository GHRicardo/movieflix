package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.MovieRepository
import javax.inject.Inject

class GetSomeRandomMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository) {
    suspend fun invoke() = movieRepository.getSomeRandomMovie()
}