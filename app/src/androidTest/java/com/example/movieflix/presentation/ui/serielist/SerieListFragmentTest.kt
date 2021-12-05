package com.example.movieflix.presentation.ui.serielist

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
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.repository.serie.FakeSerieRepositoryAndroidTestImpl
import com.example.movieflix.domain.usecase.GetPopularSeriesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedSeriesUseCase
import com.example.movieflix.getOrAwaitValue
import com.example.movieflix.other.Constants.TYPE_POPULAR
import com.example.movieflix.other.Constants.TYPE_TOP_RATED
import com.example.movieflix.presentation.ui.TestSerieFragmentFactory
import com.example.movieflix.selectTabAtPosition
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class SerieListFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var serieListViewModel: SerieListViewModel
    private var topRatedSeries = mutableListOf<Serie>()
    private var popularSeries = mutableListOf<Serie>()

    @Before
    fun setup() {
        val series = mutableListOf<Serie>()
        for (i in 0..5) {
            topRatedSeries.add(
                Serie(
                    2 * i, "", "", "nameTopRated${i % 3}", "",
                    "", "", 0.0, "", 0.0,
                    0, TYPE_TOP_RATED
                )
            )
            popularSeries.add(
                Serie(
                    2 * i + 1, "", "", "namePopular${i % 3}", "",
                    "", "", 0.0, "", 0.0,
                    0, TYPE_POPULAR
                )
            )

        }
        series.addAll(topRatedSeries)
        series.addAll(popularSeries)

        val fakeSerieRepositoryImpl = FakeSerieRepositoryAndroidTestImpl(series, emptyMap())
        serieListViewModel = SerieListViewModel(
            GetPopularSeriesUseCase(fakeSerieRepositoryImpl),
            GetTopRatedSeriesUseCase(fakeSerieRepositoryImpl)
        )
    }

    @Test
    fun clickTopRatedTabReturnsTopRatedSeriesList(){
        var testViewModel:SerieListViewModel?=null
        launchFragmentInContainer<SeriesListFragment>(
            factory = TestSerieFragmentFactory(serieListViewModel)
        ).onFragment{
            testViewModel = it.serieListViewModel
        }

        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))

        val resourceTopRatedList = testViewModel?.seriesLiveData?.getOrAwaitValue()
        val series = resourceTopRatedList?.data

        Truth.assertThat(series).containsExactlyElementsIn(topRatedSeries)
    }

    @Test
    fun clickPopularTabReturnsPopularSeriesList(){
        var testViewModel:SerieListViewModel?=null
        launchFragmentInContainer<SeriesListFragment>(
            factory = TestSerieFragmentFactory(serieListViewModel)
        ).onFragment{
            testViewModel = it.serieListViewModel
        }

        onView(withId(R.id.tabs)).perform(selectTabAtPosition(0))

        val resourceTopRatedList = testViewModel?.seriesLiveData?.getOrAwaitValue()
        val series = resourceTopRatedList?.data

        Truth.assertThat(series).containsExactlyElementsIn(popularSeries)
    }

    @Test
    fun clickOnAdapterItemNavigateToSerieDetailActivity(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<SeriesListFragment>(
            factory = TestSerieFragmentFactory(serieListViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<SerieListAdapter.SerieViewHolder>(
                1,
                click()
            )
        )

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.serieDetailActivity)
    }

    @Test
    fun clickOnSearchIconNavigateToSerieSearchFragment(){
        val navController = mock(NavController::class.java)

        launchFragmentInContainer<SeriesListFragment>(
            factory = TestSerieFragmentFactory(serieListViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.img_search)).perform(click())

        verify(navController).navigate(
            R.id.action_seriesListFragment_to_serieSearchFragment
        )
    }

    @Test
    fun clickBackIconNavigateBackToHomeFragment(){
        val navController = mock(NavController::class.java)

        launchFragmentInContainer<SeriesListFragment>(
            factory = TestSerieFragmentFactory(serieListViewModel)
        ).onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.img_back)).perform(click())

        verify(navController).popBackStack()
    }
}