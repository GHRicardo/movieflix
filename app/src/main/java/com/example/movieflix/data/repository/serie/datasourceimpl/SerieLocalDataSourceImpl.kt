package com.example.movieflix.data.repository.serie.datasourceimpl

import com.example.movieflix.data.db.SerieDao
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.repository.serie.datasource.SerieLocalDataSource
import com.example.movieflix.other.Constants

class SerieLocalDataSourceImpl(private val serieDao: SerieDao) : SerieLocalDataSource {
    override suspend fun insertSeriesToDB(series: List<Serie>) {
        serieDao.insertSeries(series)
    }

    override suspend fun getTopRatedSeriesFromDB(): List<Serie> {
        return serieDao.getSeriesByType(Constants.TYPE_TOP_RATED)
    }

    override suspend fun getPopularSeriesFromDB(): List<Serie> {
        return serieDao.getSeriesByType(Constants.TYPE_POPULAR)
    }

    override suspend fun deleteTopRatedSeries() {
        serieDao.deleteSeriesByType(Constants.TYPE_TOP_RATED)
    }

    override suspend fun deletePopularSeries() {
        serieDao.deleteSeriesByType(Constants.TYPE_POPULAR)
    }
}