package com.example.movieflix.data.repository.serie.datasource

import com.example.movieflix.data.model.series.SerieResult
import com.example.movieflix.data.model.video.VideoResult
import com.example.movieflix.other.Resource

interface SerieRemoteDataSource {
    suspend fun getPopularSeriesFromApi():Resource<SerieResult>
    suspend fun getTopRatedSeriesFromApi():Resource<SerieResult>
    suspend fun searchSeriesByName(name:String):Resource<SerieResult>
    suspend fun getVideosFromSerie(movieId:Int):Resource<VideoResult>
}