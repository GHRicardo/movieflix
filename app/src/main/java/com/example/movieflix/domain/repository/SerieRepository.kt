package com.example.movieflix.domain.repository

import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.other.Resource

interface SerieRepository {

    suspend fun getTopRatedSeries(): Resource<List<Serie>>

    suspend fun getPopularSeries(): Resource<List<Serie>>

    suspend fun searchSerieByName(name:String): Resource<List<Serie>>

    suspend fun getVideosFromSerie(serieId:Int):Resource<List<Video>>
}