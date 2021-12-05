package com.example.movieflix.presentation.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movieflix.R
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.repository.movie.FakeMovieRepositoryAndroidTestImpl
import com.example.movieflix.domain.usecase.GetSomeRandomMovieUseCase
import com.example.movieflix.presentation.ui.TestMovieFragmentFactory
import com.example.movieflix.presentation.ui.movielist.MoviesListFragment
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
            GetSomeRandomMovieUseCase(FakeMovieRepositoryAndroidTestImpl(movies, emptyMap()))
        )
    }

    @Test
    fun ifThereIsRandomMovieShowMovieDescription(){
        launchFragmentInContainer<HomeFragment>(
            factory = TestMovieFragmentFactory(homeViewModel = homeViewModel)
        )

        onView(withId(R.id.crd_movie_info)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnNextButtonNavigateToMovieDetail(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<HomeFragment>(
            factory = TestMovieFragmentFactory(homeViewModel= homeViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.crd_btn_ver)).perform(click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.movieDetailActivity)
    }

    @Test
    fun clickOnSeriesTextNavigateToSerieListFragment(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInContainer<HomeFragment>(
            factory = TestMovieFragmentFactory(homeViewModel = homeViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.txt_series)).perform(click())

        Mockito.verify(navController).navigate(
            R.id.action_homeFragment_to_seriesListFragment
        )
    }

    @Test
    fun clickOnMoviesTextNavigateToMovieListFragment(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInContainer<HomeFragment>(
            factory = TestMovieFragmentFactory(homeViewModel = homeViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.txt_movies)).perform(click())

        Mockito.verify(navController).navigate(
            R.id.action_homeFragment_to_moviesListFragment
        )
    }

    @Test
    fun clickOnSearchIconNavigateToMovieSearchFragment(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInContainer<MoviesListFragment>(
            factory = TestMovieFragmentFactory(homeViewModel = homeViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.img_search)).perform(click())

        Mockito.verify(navController).navigate(
            R.id.action_homeFragment_to_movieSearchFragment
        )
    }
}