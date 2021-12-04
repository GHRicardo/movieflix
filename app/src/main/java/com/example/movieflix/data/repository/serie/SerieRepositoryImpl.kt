package com.example.movieflix.data.repository.serie

import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.data.repository.serie.datasource.SerieLocalDataSource
import com.example.movieflix.data.repository.serie.datasource.SerieRemoteDataSource
import com.example.movieflix.domain.repository.SerieRepository
import com.example.movieflix.other.Constants
import com.example.movieflix.other.Resource
import com.example.movieflix.other.Status

class SerieRepositoryImpl(
    private val serieRemoteDataSource: SerieRemoteDataSource,
    private val serieLocalDataSource: SerieLocalDataSource
) : SerieRepository {
    override suspend fun getTopRatedSeries(): Resource<List<Serie>> {
        return getTopRatedSeriesFromDB()
    }

    private suspend fun getTopRatedSeriesFromDB(): Resource<List<Serie>> {
        val series = mutableListOf<Serie>()
        try {
            val serieResourceFromApi = getTopRatedSeriesFromApi()
            if (serieResourceFromApi.status == Status.SUCCESS) {
                serieResourceFromApi.data?.let { serieList ->
                    series.addAll(serieList)
                    serieLocalDataSource.deleteTopRatedSeries()
                    serieLocalDataSource.insertSeriesToDB(
                        serieList.map {
                            it.apply {
                                type = Constants.TYPE_TOP_RATED
                            }
                        })
                }
            }
            series.addAll(serieLocalDataSource.getTopRatedSeriesFromDB())
            return Resource.success(series)
        } catch (exception: Exception) {
            val exceptionMessage = exception.message.toString()
            return Resource.error(exceptionMessage)
        }
    }

    private suspend fun getTopRatedSeriesFromApi(): Resource<List<Serie>> {
        val result = serieRemoteDataSource.getTopRatedSeriesFromApi()
        if (result.status == Status.SUCCESS) {
            result.data?.let { serieResult ->
                return Resource.success(serieResult.series)
            }
        }
        return Resource.error(result.message ?: "Unexpected error")
    }

    override suspend fun getPopularSeries(): Resource<List<Serie>> {
        return getPopularSeriesFromDB()
    }

    private suspend fun getPopularSeriesFromDB(): Resource<List<Serie>> {
        val series = mutableListOf<Serie>()
        try {
            val serieResourceFromApi = getPopularSeriesFromApi()
            if (serieResourceFromApi.status == Status.SUCCESS) {
                serieResourceFromApi.data?.let { serieList ->
                    series.addAll(serieList)
                    serieLocalDataSource.deletePopularSeries()
                    serieLocalDataSource.insertSeriesToDB(
                        serieList.map {
                            it.apply {
                                type = Constants.TYPE_POPULAR
                            }
                        })
                }
            }
            series.addAll(serieLocalDataSource.getPopularSeriesFromDB())
            return Resource.success(series)
        } catch (exception: Exception) {
            val exceptionMessage = exception.message.toString()
            return Resource.error(exceptionMessage)
        }
    }

    private suspend fun getPopularSeriesFromApi(): Resource<List<Serie>> {
        val result = serieRemoteDataSource.getPopularSeriesFromApi()
        if (result.status == Status.SUCCESS) {
            result.data?.let { serieResult ->
                return Resource.success(serieResult.series)
            }
        }
        return Resource.error(result.message ?: "Unexpected error")
    }

    override suspend fun searchSerieByName(name: String): Resource<List<Serie>> {
        val result = serieRemoteDataSource.searchSeriesByName(name)
        if (result.status == Status.SUCCESS) {
            result.data?.let { serieResult ->
                return Resource.success(serieResult.series)
            }
        }
        return Resource.error(result.message ?: "Unexpected error")
    }

    override suspend fun getVideosFromSerie(serieId: Int): Resource<List<Video>> {
        val result = serieRemoteDataSource.getVideosFromSerie(serieId)
        if (result.status == Status.SUCCESS) {
            result.data?.let { videoResult ->
                return Resource.success(videoResult.videos)
            }
        }
        return Resource.error(result.message ?: "Unexpected error")
    }
}