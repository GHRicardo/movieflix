package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMovieByNameUseCase @Inject constructor(
    private val movieRepository: MovieRepository) {
    suspend fun invoke(name:String) = movieRepository.searchMovieByName(name)
}