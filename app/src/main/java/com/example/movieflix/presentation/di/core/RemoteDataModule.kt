package com.example.movieflix.presentation.di.core

import android.content.Context
import com.example.movieflix.data.api.MovieService
import com.example.movieflix.data.repository.movie.datasource.MovieRemoteDataSource
import com.example.movieflix.data.repository.movie.datasourceimpl.MovieRemoteDataSourceImpl
import com.example.movieflix.data.repository.serie.datasource.SerieRemoteDataSource
import com.example.movieflix.data.repository.serie.datasourceimpl.SerieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(
        movieService: MovieService,
        context: Context
    ):MovieRemoteDataSource{
        return MovieRemoteDataSourceImpl(movieService, context)
    }

    @Singleton
    @Provides
    fun provideSerieRemoteDataSource(
        movieService: MovieService,
        context: Context
    ):SerieRemoteDataSource{
        return SerieRemoteDataSourceImpl(movieService, context)
    }
}