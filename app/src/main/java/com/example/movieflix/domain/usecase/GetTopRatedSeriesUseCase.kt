package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.SerieRepository
import javax.inject.Inject

class GetTopRatedSeriesUseCase @Inject constructor(
    private val serieRepository: SerieRepository) {
    suspend fun invoke() = serieRepository.getTopRatedSeries()
}