package com.example.movieflix.presentation.ui.seriesearch

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
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.repository.serie.FakeSerieRepositoryAndroidTestImpl
import com.example.movieflix.domain.usecase.SearchSerieByNameUseCase
import com.example.movieflix.getOrAwaitValue
import com.example.movieflix.other.Constants.TEST_WAIT_EDIT_TIME_DELAY
import com.example.movieflix.other.Constants.TYPE_POPULAR
import com.example.movieflix.other.Constants.TYPE_TOP_RATED
import com.example.movieflix.presentation.ui.TestSerieFragmentFactory
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
class SerieSearchFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var serieSearchViewModel: SerieSearchViewModel

    @Before
    fun setup(){
        val series = mutableListOf<Serie>()
        for (i in 0..5) {
            series.add(
                Serie(
                    2 * i, "", "", "nameTopRated${i % 3}", "",
                    "", "", 0.0, "", 0.0,
                    0, TYPE_TOP_RATED
                )
            )
            series.add(
                Serie(
                    2 * i + 1, "", "", "namePopular${i % 3}", "",
                    "", "", 0.0, "", 0.0,
                    0, TYPE_POPULAR
                )
            )

        }

        serieSearchViewModel = SerieSearchViewModel(
            SearchSerieByNameUseCase(FakeSerieRepositoryAndroidTestImpl(series, emptyMap()))
        )
    }

    @Test
    fun writeExisitingNameBringsNotEmptyList(){
        var testViewModel:SerieSearchViewModel? = null

        launchFragmentInContainer<SerieSearchFragment>(
            factory = TestSerieFragmentFactory(serieSearchViewModel = serieSearchViewModel)
        ).onFragment {
            testViewModel = it.serieSearchViewModel
        }

        onView(withId(R.id.etxt_search)).perform(replaceText("namePopular1"))
        onView(isRoot()).perform(waitFor(TEST_WAIT_EDIT_TIME_DELAY))

        val resourceSerie = testViewModel?.searchedSeriesLiveData?.getOrAwaitValue()
        val series = resourceSerie?.data
        Truth.assertThat(series).isNotEmpty()
    }

    @Test
    fun writeNotExisitingNameBringsEmptyList(){
        var testViewModel:SerieSearchViewModel? = null

        launchFragmentInContainer<SerieSearchFragment>(
            factory = TestSerieFragmentFactory(serieSearchViewModel = serieSearchViewModel)
        ).onFragment {
            testViewModel = it.serieSearchViewModel
        }

        onView(withId(R.id.etxt_search)).perform(replaceText("wrongName"))
        onView(isRoot()).perform(waitFor(TEST_WAIT_EDIT_TIME_DELAY))

        val resourceSerie = testViewModel?.searchedSeriesLiveData?.getOrAwaitValue()
        val series = resourceSerie?.data
        Truth.assertThat(series).isEmpty()
    }

    @Test
    fun writeNotExisitingNameShowsEmptyMessageTextView(){
        launchFragmentInContainer<SerieSearchFragment>(
            factory = TestSerieFragmentFactory(serieSearchViewModel = serieSearchViewModel)
        )

        onView(withId(R.id.etxt_search)).perform(replaceText("wrongName"))
        onView(isRoot()).perform(waitFor(TEST_WAIT_EDIT_TIME_DELAY))

        onView(withId(R.id.txt_no_data)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnAdapterItemNavigateToSerieDetailActivity(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<SerieSearchFragment>(
            factory = TestSerieFragmentFactory(serieSearchViewModel = serieSearchViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.etxt_search)).perform(replaceText("namePopular1"))
        onView(isRoot()).perform(waitFor(TEST_WAIT_EDIT_TIME_DELAY))

        onView(withId(R.id.rv_search_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<SerieSearchAdapter.SerieViewHolder>(
                1,
                click()
            )
        )

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.serieDetailActivity)
    }

    @Test
    fun clickClearIconClearsEditTextText(){
        launchFragmentInContainer<SerieSearchFragment>(
            factory = TestSerieFragmentFactory(serieSearchViewModel = serieSearchViewModel)
        )

        onView(withId(R.id.etxt_search)).perform(replaceText("wrongName"))
        onView(withId(R.id.lyt_btn_close)).perform(click())

        onView(withId(R.id.etxt_search)).check(matches(withText(`is`(emptyString()))))
    }

    @Test
    fun clickBackIconNavigateBackToHomeFragment(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInContainer<SerieSearchFragment>(
            factory = TestSerieFragmentFactory(serieSearchViewModel = serieSearchViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.img_back)).perform(ViewActions.click())

        Mockito.verify(navController).popBackStack()
    }
}