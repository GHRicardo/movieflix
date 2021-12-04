package com.example.movieflix.data.repository.serie.datasourceimpl

import android.content.Context
import com.example.movieflix.data.api.BaseRemoteDataSource
import com.example.movieflix.data.api.MovieService
import com.example.movieflix.data.repository.serie.datasource.SerieRemoteDataSource

class SerieRemoteDataSourceImpl(
    private val movieService: MovieService,
    context: Context
    ): SerieRemoteDataSource, BaseRemoteDataSource(context) {
    override suspend fun getPopularSeriesFromApi() =
        getResult { movieService.getPopularSeries() }

    override suspend fun getTopRatedSeriesFromApi() =
        getResult { movieService.getTopRatedSeries() }

    override suspend fun searchSeriesByName(name: String) =
        getResult { movieService.searchSerieByName(name) }

    override suspend fun getVideosFromSerie(serieId: Int) =
        getResult { movieService.getVideosFromSerie(serieId) }
}