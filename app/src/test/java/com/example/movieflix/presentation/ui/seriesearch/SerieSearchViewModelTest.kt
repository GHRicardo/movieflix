package com.example.movieflix.presentation.ui.seriesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieflix.MainCoroutineRule
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.repository.serie.FakeSerieRepositoryImpl
import com.example.movieflix.domain.usecase.SearchSerieByNameUseCase
import com.example.movieflix.getOrAwaitValueTest
import com.example.movieflix.other.Constants.TYPE_POPULAR
import com.example.movieflix.other.Constants.TYPE_TOP_RATED
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SerieSearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var serieSearchViewModel: SerieSearchViewModel

    @Before
    fun setup(){
        val series = mutableListOf<Serie>()
        for(i in 0..5){
            series.add(
                Serie(2*i, "", "", "nameTopRated${i%3}", "",
                    "", "", 0.0, "", 0.0,
                    0, TYPE_TOP_RATED)
            )
            series.add(
                Serie(2*i + 1, "", "", "namePopular${i%3}", "",
                    "", "", 0.0, "", 0.0,
                    0, TYPE_POPULAR)
            )
        }

        serieSearchViewModel =
            SerieSearchViewModel(
                SearchSerieByNameUseCase(FakeSerieRepositoryImpl(series, emptyMap()))
            )
    }

    @Test
    fun `search serie with wrong name returns empty list`(){
        val query = "wrongName"
        serieSearchViewModel.searchSeriesByName(query)
        val resourceSeries = serieSearchViewModel.searchedSeriesLiveData.getOrAwaitValueTest()
        val series = resourceSeries.data
        assertThat(series).isEmpty()
    }

    @Test
    fun `search serie with right name returns not empty list`(){
        val query = "namePopular1"
        serieSearchViewModel.searchSeriesByName(query)
        val resourceSeries = serieSearchViewModel.searchedSeriesLiveData.getOrAwaitValueTest()
        val series = resourceSeries.data
        assertThat(series).isNotEmpty()
    }

}