package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.MovieRepository

class SearchMovieByNameUseCase(
    private val movieRepository: MovieRepository
    ) {
    suspend fun invoke(name:String) = movieRepository.searchMovieByName(name)
}