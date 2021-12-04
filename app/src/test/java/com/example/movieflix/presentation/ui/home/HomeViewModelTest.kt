package com.example.movieflix.presentation.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieflix.MainCoroutineRule
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.repository.movie.FakeMovieRepositoryImpl
import com.example.movieflix.domain.usecase.GetSomeRandomMovieUseCase
import com.example.movieflix.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup(){
        val movies = mutableListOf<Movie>()
        for(i in 0..5){
            movies.add(
                Movie(
                    i, "", "", "", "",
                    0.0, "", "", "",
                    0.0, 0, ""
                )
            )
        }

        homeViewModel = HomeViewModel(
            GetSomeRandomMovieUseCase(FakeMovieRepositoryImpl(movies, emptyMap()))
        )
    }

    @Test
    fun `get some random movie returns only one movie`(){
        homeViewModel.getSomeRandomMovie()
        val resourceMovie = homeViewModel.randomMovieLiveData.getOrAwaitValueTest()
        val movie = resourceMovie.data
        assertThat(movie).isInstanceOf(Movie::class.java)
    }
}