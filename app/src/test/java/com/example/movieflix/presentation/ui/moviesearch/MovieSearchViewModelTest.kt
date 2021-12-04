package com.example.movieflix.presentation.ui.moviesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieflix.MainCoroutineRule
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.repository.movie.FakeMovieRepositoryImpl
import com.example.movieflix.domain.usecase.SearchMovieByNameUseCase
import com.example.movieflix.getOrAwaitValueTest
import com.example.movieflix.other.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieSearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var movieSearchViewModel: MovieSearchViewModel

    @Before
    fun setup(){
        val movies = mutableListOf<Movie>()
        for (i in 0..5) {
            movies.add(
                Movie(
                    2 * i, "", "", "", "",
                    0.0, "", "", "titleNotRated${i % 3}",
                    0.0, 0, Constants.TYPE_TOP_RATED
                )
            )
            movies.add(
                Movie(
                    2 * i + 1, "", "", "", "",
                    0.0, "", "", "titlePopular${i % 3}",
                    0.0, 0, Constants.TYPE_POPULAR
                )
            )

        }

        movieSearchViewModel = MovieSearchViewModel(
            SearchMovieByNameUseCase(FakeMovieRepositoryImpl(movies, emptyMap()))
        )
    }

    @Test
    fun `search movie with wrong name returns empty list`(){
        val query = "wrongName"
        movieSearchViewModel.searchMoviesByName(query)
        val resourceMovies = movieSearchViewModel.searchedMoviesLiveData.getOrAwaitValueTest()
        val movies = resourceMovies.data
        assertThat(movies).isEmpty()
    }

    @Test
    fun `search movie with right name returns not empty list`(){
        val query = "titlePopular1"
        movieSearchViewModel.searchMoviesByName(query)
        val resourceMovies = movieSearchViewModel.searchedMoviesLiveData.getOrAwaitValueTest()
        val movies = resourceMovies.data
        assertThat(movies).isNotEmpty()
    }
}