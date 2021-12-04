package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.SerieRepository
import javax.inject.Inject

class GetPopularSeriesUseCase @Inject constructor(
    private val serieRepository: SerieRepository) {
    suspend fun invoke() = serieRepository.getPopularSeries()
}