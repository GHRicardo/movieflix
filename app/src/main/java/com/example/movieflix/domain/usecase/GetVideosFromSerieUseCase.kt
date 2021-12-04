package com.example.movieflix.domain.usecase

import com.example.movieflix.domain.repository.SerieRepository
import javax.inject.Inject

class GetVideosFromSerieUseCase @Inject constructor(
    private val serieRepository: SerieRepository) {
    suspend fun invoke(serieID:Int) = serieRepository.getVideosFromSerie(serieID)
}