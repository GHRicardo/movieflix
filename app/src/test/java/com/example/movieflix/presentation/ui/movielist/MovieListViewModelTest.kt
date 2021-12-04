package com.example.movieflix.presentation.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieflix.MainCoroutineRule
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.repository.movie.FakeMovieRepositoryImpl
import com.example.movieflix.domain.usecase.GetPopularMoviesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedMoviesUseCase
import com.example.movieflix.getOrAwaitValueTest
import com.example.movieflix.other.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var movieListViewModel: MovieListViewModel

    @Before
    fun setup() {
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
        movies.add(
            Movie(
                12, "", "", "", "",
                0.0, "", "", "titlePopular12",
                0.0, 0, Constants.TYPE_POPULAR
            )
        )

        val fakeMovieRepositoryImpl = FakeMovieRepositoryImpl(movies, emptyMap())
        movieListViewModel = MovieListViewModel(
            GetPopularMoviesUseCase(fakeMovieRepositoryImpl),
            GetTopRatedMoviesUseCase(fakeMovieRepositoryImpl)
        )
    }

    @Test
    fun `get top rated movies brings 6 elements`() {
        movieListViewModel.getTopRatedMovies()
        val resourceMovies = movieListViewModel.moviesLiveData.getOrAwaitValueTest()
        val movies = resourceMovies.data
        assertThat(movies).hasSize(6)
    }

    @Test
    fun `get popular movies brings 7 elements`() {
        movieListViewModel.getPopularMovies()
        val resourceMovies = movieListViewModel.moviesLiveData.getOrAwaitValueTest()
        val movies = resourceMovies.data
        assertThat(movies).hasSize(7)
    }
}