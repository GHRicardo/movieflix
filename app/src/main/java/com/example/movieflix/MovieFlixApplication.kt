package com.example.movieflix

import android.app.Application
import com.example.movieflix.presentation.di.Injector
import com.example.movieflix.presentation.di.core.AppComponent
import com.example.movieflix.presentation.di.core.DaggerAppComponent
import com.example.movieflix.presentation.di.home.HomeSubComponent
import com.example.movieflix.presentation.di.movie.MovieSubcomponent
import com.example.movieflix.presentation.di.serie.SerieSubcomponent

class MovieFlixApplication : Application(), Injector {
    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent() : AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun createMovieSubcomponent(): MovieSubcomponent {
        return appComponent.movieSubcomponent().create()
    }

    override fun createHomeSubComponent(): HomeSubComponent {
        return appComponent.homeSubcomponent().create()
    }

    override fun createSerieSubComponent(): SerieSubcomponent {
        return appComponent.serieSubcomponent().create()
    }
}