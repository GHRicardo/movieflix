package com.example.movieflix.presentation.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieflix.MainCoroutineRule
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.data.repository.movie.FakeMovieRepositoryImpl
import com.example.movieflix.domain.usecase.GetVideosFromMovieUseCase
import com.example.movieflix.getOrAwaitValueTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setup(){
        val videos = mutableListOf<Video>()
        for(i in 0..5){
            videos.add(
                Video("$i","","", "", "", false,
                    "", "", 0, "", "")
            )
        }
        val videosByMovieId = mapOf(0 to videos, 1 to emptyList())
        movieDetailViewModel =
            MovieDetailViewModel(
                GetVideosFromMovieUseCase(
                    FakeMovieRepositoryImpl(emptyList(), videosByMovieId)
                )
            )
    }

    @Test
    fun `get videos from serieId 0 returns a not empty list`(){
        movieDetailViewModel.getVideosFromMovie(0)
        val resourceVideos = movieDetailViewModel.videosFromMovieLiveData.getOrAwaitValueTest()
        val videos = resourceVideos.data
        Truth.assertThat(videos).isNotEmpty()
    }

    @Test
    fun `get videos from serieId 1 returns an empty list`(){
        movieDetailViewModel.getVideosFromMovie(1)
        val resourceVideos = movieDetailViewModel.videosFromMovieLiveData.getOrAwaitValueTest()
        val videos = resourceVideos.data
        Truth.assertThat(videos).isEmpty()
    }
}