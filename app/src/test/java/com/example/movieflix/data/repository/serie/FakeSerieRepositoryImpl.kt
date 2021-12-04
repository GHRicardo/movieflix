package com.example.movieflix.data.repository.serie

import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.domain.repository.SerieRepository
import com.example.movieflix.other.Constants.TYPE_POPULAR
import com.example.movieflix.other.Constants.TYPE_TOP_RATED
import com.example.movieflix.other.Resource

class FakeSerieRepositoryImpl(
    private val series: List<Serie>,
    private val videosBySerieId: Map<Int, List<Video>>
) : SerieRepository{

    override suspend fun getTopRatedSeries(): Resource<List<Serie>> {
        return Resource.success(series.filter { it.type == TYPE_TOP_RATED })
    }

    override suspend fun getPopularSeries(): Resource<List<Serie>> {
        return Resource.success(series.filter { it.type == TYPE_POPULAR })
    }

    override suspend fun searchSerieByName(name: String): Resource<List<Serie>> {
        return Resource.success(series.filter { it.name == name })
    }

    override suspend fun getVideosFromSerie(serieId: Int): Resource<List<Video>> {
        return Resource.success(videosBySerieId.get(serieId))
    }
}