package com.example.movieflix

import com.example.movieflix.presentation.di.core.AppComponent
import com.example.movieflix.presentation.di.core.DaggerTestAppComponent
import com.example.movieflix.presentation.di.core.TestAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TestMovieFlixApplication : MovieFlixApplication() {

    val testAppComponent: TestAppComponent by lazy {
        initializeComponent() as TestAppComponent
    }

    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.factory().create(applicationContext)
    }

}