package com.example.movieflix.presentation.di.core

import android.content.Context
import com.example.movieflix.presentation.di.home.HomeSubComponent
import com.example.movieflix.presentation.di.movie.MovieSubcomponent
import com.example.movieflix.presentation.di.serie.SerieSubcomponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApiServiceModule::class,
    DatabaseModule::class,
    LocalDataModule::class,
    RemoteDataModule::class,
    RepositoryModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun movieSubcomponent():MovieSubcomponent.Factory
    fun homeSubcomponent():HomeSubComponent.Factory
    fun serieSubcomponent():SerieSubcomponent.Factory

}