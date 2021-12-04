package com.example.movieflix.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import com.example.movieflix.TestMovieFlixApplication
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.presentation.di.core.DaggerTestAppComponent
import com.example.movieflix.presentation.di.core.TestAppComponent
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
class MovieDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database:MovieDatabase

    private lateinit var movieDao:MovieDao

    @Before
    fun setup(){
        /*val component: TestAppComponent = DaggerTestAppComponent.factory()
            .create(ApplicationProvider.getApplicationContext())
        component.inject(this)*/
        (ApplicationProvider.getApplicationContext<TestMovieFlixApplication>())
            .testAppComponent.inject(this)
        movieDao = database.movieDao()
    }

    @Test
    fun insertMovies() = runBlockingTest {
        val movie = Movie(1, "", "", "", "", 0.0, "", "", "", 0.0, 0, "")
        val movieList = mutableListOf<Movie>()
        movieList.add(movie)
        movieDao.insertMovies(movieList)
        val movies = movieDao.getMoviesByType("")
        assertThat(movies).isNotEmpty()
    }

    @Test
    fun deleteMoviesByType2LeaveTwoElementsOnTable() = runBlockingTest {
        val movie1 = Movie(1, "", "", "", "", 0.0, "", "", "", 0.0, 0, "type1")
        val movie2 = Movie(2, "", "", "", "", 0.0, "", "", "", 0.0, 0, "type2")
        val movie3 = Movie(3, "", "", "", "", 0.0, "", "", "", 0.0, 0, "type1")
        val movieList = mutableListOf<Movie>()
        movieList.add(movie1)
        movieList.add(movie2)
        movieList.add(movie3)
        movieDao.insertMovies(movieList)
        movieDao.deleteMoviesByType("type2")
        val movies = movieDao.getMovies()
        assertThat(movies).hasSize(2)
    }

    @Test
    fun getMoviesByType2BringsOneElement() = runBlockingTest {
        val movie1 = Movie(1, "", "", "", "", 0.0, "", "", "", 0.0, 0, "type1")
        val movie2 = Movie(2, "", "", "", "", 0.0, "", "", "", 0.0, 0, "type2")
        val movie3 = Movie(3, "", "", "", "", 0.0, "", "", "", 0.0, 0, "type1")
        val movieList = mutableListOf<Movie>()
        movieList.add(movie1)
        movieList.add(movie2)
        movieList.add(movie3)
        movieDao.insertMovies(movieList)
        val movies = movieDao.getMoviesByType("type2")
        assertThat(movies).hasSize(1)
    }

    @After
    fun tearDown(){
        database.close()
    }
}