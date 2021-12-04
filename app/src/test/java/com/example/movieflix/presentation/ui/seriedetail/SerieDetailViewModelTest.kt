package com.example.movieflix.presentation.ui.seriedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieflix.MainCoroutineRule
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.data.repository.serie.FakeSerieRepositoryImpl
import com.example.movieflix.domain.usecase.GetVideosFromSerieUseCase
import com.example.movieflix.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SerieDetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var serieDetailViewModel: SerieDetailViewModel

    @Before
    fun setup(){
        val videos = mutableListOf<Video>()
        for(i in 0..5){
            videos.add(
                Video("$i","","", "", "", false,
                "", "", 0, "", "")
            )
        }
        val videosBySerieId = mapOf(0 to videos, 1 to emptyList())
        serieDetailViewModel =
            SerieDetailViewModel(
                GetVideosFromSerieUseCase(
                    FakeSerieRepositoryImpl(emptyList(), videosBySerieId)
                )
            )
    }

    @Test
    fun `get videos from serieId 0 returns a not empty list`(){
        serieDetailViewModel.getVideosFromSerie(0)
        val resourceVideos = serieDetailViewModel.videosFromSerieLiveData.getOrAwaitValueTest()
        val videos = resourceVideos.data
        assertThat(videos).isNotEmpty()
    }

    @Test
    fun `get videos from serieId 1 returns an empty list`(){
        serieDetailViewModel.getVideosFromSerie(1)
        val resourceVideos = serieDetailViewModel.videosFromSerieLiveData.getOrAwaitValueTest()
        val videos = resourceVideos.data
        assertThat(videos).isEmpty()
    }
}