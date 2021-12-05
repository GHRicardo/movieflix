package com.example.movieflix.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.movieflix.TestMovieFlixApplication
import com.example.movieflix.data.model.series.Serie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
class SerieDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database:MovieDatabase

    private lateinit var serieDao: SerieDao

    @Before
    fun setup(){
        (ApplicationProvider.getApplicationContext<TestMovieFlixApplication>())
            .testAppComponent.inject(this)
        serieDao = database.serieDao()
    }

    @Test
    fun insertSeries() = runBlockingTest {
        val serie = Serie(1, "", "", "", "", "", "", 0.0, "", 0.0, 0, "")
        val serieList = mutableListOf<Serie>()
        serieList.add(serie)
        serieDao.insertSeries(serieList)
        val movies = serieDao.getSeriesByType("")
        assertThat(movies).isNotEmpty()
    }

    @Test
    fun deleteSeriesByType2LeaveTwoElementsOnTable() = runBlockingTest {
        val serie1 = Serie(1, "", "", "", "", "", "", 0.0, "", 0.0, 0, "type1")
        val serie2 = Serie(2, "", "", "", "", "", "", 0.0, "", 0.0, 0, "type2")
        val serie3 = Serie(3, "", "", "", "", "", "", 0.0, "", 0.0, 0, "type1")
        val serieList = mutableListOf<Serie>()
        serieList.add(serie1)
        serieList.add(serie2)
        serieList.add(serie3)
        serieDao.insertSeries(serieList)
        serieDao.deleteSeriesByType("type2")
        val movies = serieDao.getSeries()
        assertThat(movies).hasSize(2)
    }

    @Test
    fun getSeriesByType2BringsOneElement() = runBlockingTest {
        val serie1 = Serie(1, "", "", "", "", "", "", 0.0, "", 0.0, 0, "type1")
        val serie2 = Serie(2, "", "", "", "", "", "", 0.0, "", 0.0, 0, "type2")
        val serie3 = Serie(3, "", "", "", "", "", "", 0.0, "", 0.0, 0, "type1")
        val serieList = mutableListOf<Serie>()
        serieList.add(serie1)
        serieList.add(serie2)
        serieList.add(serie3)
        serieDao.insertSeries(serieList)
        val movies = serieDao.getSeriesByType("type2")
        assertThat(movies).hasSize(1)
    }

    @After
    fun tearDown(){
        database.close()
    }
}