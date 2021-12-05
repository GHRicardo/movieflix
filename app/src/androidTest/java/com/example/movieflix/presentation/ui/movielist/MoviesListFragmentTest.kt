package com.example.movieflix.presentation.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movieflix.R
import com.example.movieflix.getOrAwaitValue
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.repository.movie.FakeMovieRepositoryAndroidTestImpl
import com.example.movieflix.domain.usecase.GetPopularMoviesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedMoviesUseCase
import com.example.movieflix.other.Constants.TYPE_POPULAR
import com.example.movieflix.other.Constants.TYPE_TOP_RATED
import com.example.movieflix.presentation.ui.TestMovieFragmentFactory
import com.example.movieflix.selectTabAtPosition
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class MoviesListFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieListViewModel : MovieListViewModel
    private var topRatedMovies = mutableListOf<Movie>()
    private var popularMovies = mutableListOf<Movie>()

    @Before
    fun setup() {
        val movies = mutableListOf<Movie>()
        for (i in 0..5) {
            topRatedMovies.add(
                Movie(
                    2 * i, "", "", "", "",
                    0.0, "", "", "titleNotRated${i % 3}",
                    0.0, 0, TYPE_TOP_RATED
                )
            )
            popularMovies.add(
                Movie(
                    2 * i + 1, "", "", "", "",
                    0.0, "", "", "titlePopular${i % 3}",
                    0.0, 0, TYPE_POPULAR
                )
            )

        }
        movies.addAll(topRatedMovies)
        movies.addAll(popularMovies)

        val fakeMovieRepositoryImpl = FakeMovieRepositoryAndroidTestImpl(movies, emptyMap())
        movieListViewModel = MovieListViewModel(
            GetPopularMoviesUseCase(fakeMovieRepositoryImpl),
            GetTopRatedMoviesUseCase(fakeMovieRepositoryImpl)
        )
    }

    @Test
    fun clickTopRatedTabReturnsTopRatedMoviesList(){
        var testViewModel:MovieListViewModel?=null
        launchFragmentInContainer<MoviesListFragment>(
            factory = TestMovieFragmentFactory(movieListViewModel)
        ).onFragment{
            testViewModel = it.movieListViewModel
        }

        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))

        val resourceTopRatedList = testViewModel?.moviesLiveData?.getOrAwaitValue()
        val movies = resourceTopRatedList?.data

        Truth.assertThat(movies).containsExactlyElementsIn(topRatedMovies)
    }

    @Test
    fun clickPopularTabReturnsPopularMoviesList(){
        var testViewModel:MovieListViewModel?=null
        launchFragmentInContainer<MoviesListFragment>(
            factory = TestMovieFragmentFactory(movieListViewModel)
        ).onFragment{
            testViewModel = it.movieListViewModel
        }

        onView(withId(R.id.tabs)).perform(selectTabAtPosition(0))

        val resourceTopRatedList = testViewModel?.moviesLiveData?.getOrAwaitValue()
        val movies = resourceTopRatedList?.data

        Truth.assertThat(movies).containsExactlyElementsIn(popularMovies)
    }

    @Test
    fun clickOnAdapterItemNavigateToMovieDetailActivity(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<MoviesListFragment>(
            factory = TestMovieFragmentFactory(movieListViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieViewHolder>(
                1,
                click()
            )
        )

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.movieDetailActivity)
    }

    @Test
    fun clickOnSearchIconNavigateToMovieSearchFragment(){
        val navController = mock(NavController::class.java)

        launchFragmentInContainer<MoviesListFragment>(
            factory = TestMovieFragmentFactory(movieListViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.img_search)).perform(click())

        verify(navController).navigate(
            R.id.action_moviesListFragment_to_movieSearchFragment
        )
    }

    @Test
    fun clickBackIconNavigateBackToHomeFragment(){
        val navController = mock(NavController::class.java)

        launchFragmentInContainer<MoviesListFragment>(
            factory = TestMovieFragmentFactory(movieListViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.img_back)).perform(click())

        verify(navController).popBackStack()
    }
}