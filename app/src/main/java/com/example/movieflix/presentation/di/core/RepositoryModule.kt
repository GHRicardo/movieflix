package com.example.movieflix.presentation.di.core

import com.example.movieflix.data.repository.movie.MovieRepositoryImpl
import com.example.movieflix.data.repository.movie.datasource.MovieLocalDataSource
import com.example.movieflix.data.repository.movie.datasource.MovieRemoteDataSource
import com.example.movieflix.data.repository.serie.SerieRepositoryImpl
import com.example.movieflix.data.repository.serie.datasource.SerieLocalDataSource
import com.example.movieflix.data.repository.serie.datasource.SerieRemoteDataSource
import com.example.movieflix.domain.repository.MovieRepository
import com.example.movieflix.domain.repository.SerieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ):MovieRepository{
        return MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideSerieRepository(
        serieRemoteDataSource: SerieRemoteDataSource,
        serieLocalDataSource: SerieLocalDataSource
    ):SerieRepository{
        return SerieRepositoryImpl(serieRemoteDataSource, serieLocalDataSource)
    }
}