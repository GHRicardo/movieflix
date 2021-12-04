package com.example.movieflix.data.repository.serie.datasource

import com.example.movieflix.data.model.series.Serie

interface SerieLocalDataSource {
    suspend fun insertSeriesToDB(series:List<Serie>)
    suspend fun getTopRatedSeriesFromDB():List<Serie>
    suspend fun getPopularSeriesFromDB():List<Serie>
    suspend fun deleteTopRatedSeries()
    suspend fun deletePopularSeries()
}