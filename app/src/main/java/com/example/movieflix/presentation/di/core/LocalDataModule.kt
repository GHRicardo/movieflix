package com.example.movieflix.presentation.di.core

import com.example.movieflix.data.db.MovieDao
import com.example.movieflix.data.db.SerieDao
import com.example.movieflix.data.repository.movie.datasource.MovieLocalDataSource
import com.example.movieflix.data.repository.movie.datasourceimpl.MovieLocalDataSourceImpl
import com.example.movieflix.data.repository.serie.datasource.SerieLocalDataSource
import com.example.movieflix.data.repository.serie.datasourceimpl.SerieLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun provideMovieLocalDataSource(movieDao: MovieDao):MovieLocalDataSource{
        return MovieLocalDataSourceImpl(movieDao)
    }

    @Singleton
    @Provides
    fun provideSerieLocalDataSource(serieDao: SerieDao):SerieLocalDataSource{
        return SerieLocalDataSourceImpl(serieDao)
    }
}