package com.example.movieflix.presentation.ui.moviesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movieflix.R
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.data.repository.movie.FakeMovieRepositoryAndroidTestImpl
import com.example.movieflix.domain.usecase.SearchMovieByNameUseCase
import com.example.movieflix.getOrAwaitValue
import com.example.movieflix.other.Constants
import com.example.movieflix.other.Constants.TEST_WAIT_EDIT_TIME_DELAY
import com.example.movieflix.presentation.ui.TestMovieFragmentFactory
import com.example.movieflix.waitFor
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.`is`
import org.hamcrest.text.IsEmptyString.emptyString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieSearchFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieSearchViewModel: MovieSearchViewModel

    @Before
    fun setup(){
        val movies = mutableListOf<Movie>()
        for (i in 0..5) {
            movies.add(
                Movie(
                    2 * i, "", "", "", "",
                    0.0, "", "", "titleTopRated${i % 3}",
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
            SearchMovieByNameUseCase(FakeMovieRepositoryAndroidTestImpl(movies, emptyMap()))
        )
    }

    @Test
    fun writeExisitingNameBringsNotEmptyList(){
        var testViewModel:MovieSearchViewModel? = null

        launchFragmentInContainer<MovieSearchFragment>(
            factory = TestMovieFragmentFactory(movieSearchViewModel = movieSearchViewModel)
        ).onFragment {
            testViewModel = it.movieSearchViewModel
        }

        onView(withId(R.id.etxt_search)).perform(replaceText("titlePopular1"))
        onView(isRoot()).perform(waitFor(TEST_WAIT_EDIT_TIME_DELAY))

        val resourceMovie = testViewModel?.searchedMoviesLiveData?.getOrAwaitValue()
        val movies = resourceMovie?.data
        Truth.assertThat(movies).isNotEmpty()
    }

    @Test
    fun writeNotExisitingNameBringsEmptyList(){
        var testViewModel:MovieSearchViewModel? = null

        launchFragmentInContainer<MovieSearchFragment>(
            factory = TestMovieFragmentFactory(movieSearchViewModel = movieSearchViewModel)
        ).onFragment {
            testViewModel = it.movieSearchViewModel
        }

        onView(withId(R.id.etxt_search)).perform(replaceText("wrongName"))
        onView(isRoot()).perform(waitFor(TEST_WAIT_EDIT_TIME_DELAY))

        val resourceMovie = testViewModel?.searchedMoviesLiveData?.getOrAwaitValue()
        val movies = resourceMovie?.data
        Truth.assertThat(movies).isEmpty()
    }

    @Test
    fun writeNotExisitingNameShowsEmptyMessageTextView(){
        launchFragmentInContainer<MovieSearchFragment>(
            factory = TestMovieFragmentFactory(movieSearchViewModel = movieSearchViewModel)
        )

        onView(withId(R.id.etxt_search)).perform(replaceText("wrongName"))
        onView(isRoot()).perform(waitFor(TEST_WAIT_EDIT_TIME_DELAY))

        onView(withId(R.id.txt_no_data)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnAdapterItemNavigateToMovieDetailActivity(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<MovieSearchFragment>(
            factory = TestMovieFragmentFactory(movieSearchViewModel = movieSearchViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.etxt_search)).perform(replaceText("titlePopular1"))
        onView(isRoot()).perform(waitFor(TEST_WAIT_EDIT_TIME_DELAY))

        onView(withId(R.id.rv_search_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieSearchAdapter.MovieViewHolder>(
                1,
                click()
            )
        )

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.movieDetailActivity)
    }

    @Test
    fun clickClearIconClearsEditTextText(){
        launchFragmentInContainer<MovieSearchFragment>(
            factory = TestMovieFragmentFactory(movieSearchViewModel = movieSearchViewModel)
        )

        onView(withId(R.id.etxt_search)).perform(replaceText("wrongName"))
        onView(withId(R.id.lyt_btn_close)).perform(click())

        onView(withId(R.id.etxt_search)).check(matches(withText(`is`(emptyString()))))
    }

    @Test
    fun clickBackIconNavigateBackToHomeFragment(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInContainer<MovieSearchFragment>(
            factory = TestMovieFragmentFactory(movieSearchViewModel = movieSearchViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.img_back)).perform(ViewActions.click())

        Mockito.verify(navController).popBackStack()
    }
}