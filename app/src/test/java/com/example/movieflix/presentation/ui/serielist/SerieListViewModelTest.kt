package com.example.movieflix.presentation.ui.serielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieflix.MainCoroutineRule
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.data.repository.serie.FakeSerieRepositoryImpl
import com.example.movieflix.domain.usecase.GetPopularSeriesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedSeriesUseCase
import com.example.movieflix.getOrAwaitValueTest
import com.example.movieflix.other.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SerieListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var serieListViewModel: SerieListViewModel

    @Before
    fun setup() {
        val series = mutableListOf<Serie>()
        for (i in 0..5) {
            series.add(
                Serie(
                    2 * i, "", "", "nameTopRated${i % 3}", "",
                    "", "", 0.0, "", 0.0,
                    0, Constants.TYPE_TOP_RATED
                )
            )
            series.add(
                Serie(
                    2 * i + 1, "", "", "namePopular${i % 3}", "",
                    "", "", 0.0, "", 0.0,
                    0, Constants.TYPE_POPULAR
                )
            )
        }
        series.add(
            Serie(
                12, "", "", "namePopular12", "",
                "", "", 0.0, "", 0.0,
                0, Constants.TYPE_POPULAR
            )
        )
        val fakeSerieRepository = FakeSerieRepositoryImpl(series, emptyMap())

        serieListViewModel = SerieListViewModel(
            GetPopularSeriesUseCase(fakeSerieRepository),
            GetTopRatedSeriesUseCase(fakeSerieRepository)
        )
    }

    @Test
    fun `get top rated series brings 6 elements`() {
        serieListViewModel.getTopRatedSeries()
        val resourceSeries = serieListViewModel.seriesLiveData.getOrAwaitValueTest()
        val series = resourceSeries.data
        assertThat(series).hasSize(6)
    }

    @Test
    fun `get popular series brings 7 elements`() {
        serieListViewModel.getPopularSeries()
        val resourceSeries = serieListViewModel.seriesLiveData.getOrAwaitValueTest()
        val series = resourceSeries.data
        assertThat(series).hasSize(7)
    }
}