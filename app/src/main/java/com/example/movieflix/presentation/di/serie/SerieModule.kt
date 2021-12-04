package com.example.movieflix.presentation.di.serie

import com.example.movieflix.domain.usecase.*
import com.example.movieflix.presentation.ui.moviedetail.MovieDetailViewModelFactory
import com.example.movieflix.presentation.ui.movielist.MovieListViewModelFactory
import com.example.movieflix.presentation.ui.moviesearch.MovieSearchViewModelFactory
import com.example.movieflix.presentation.ui.seriedetail.SerieDetailViewModelFactory
import com.example.movieflix.presentation.ui.serielist.SerieListViewModelFactory
import com.example.movieflix.presentation.ui.seriesearch.SerieSearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SerieModule {

    @SerieScope
    @Provides
    fun provideSerieListViewModelFactory(
        getPopularSeriesUseCase: GetPopularSeriesUseCase,
        getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase
    ):SerieListViewModelFactory{
        return SerieListViewModelFactory(
            getPopularSeriesUseCase,
            getTopRatedSeriesUseCase)
    }

    @SerieScope
    @Provides
    fun provideMSerieSearchViewModelFactory(
        searchSerieByNameUseCase: SearchSerieByNameUseCase
    ): SerieSearchViewModelFactory {
        return SerieSearchViewModelFactory(
            searchSerieByNameUseCase
        )
    }

    @SerieScope
    @Provides
    fun provideSerieDetailViewModelFactory(
        getVideosFromSerieUseCase: GetVideosFromSerieUseCase
    ): SerieDetailViewModelFactory {
        return SerieDetailViewModelFactory(
            getVideosFromSerieUseCase
        )
    }
}