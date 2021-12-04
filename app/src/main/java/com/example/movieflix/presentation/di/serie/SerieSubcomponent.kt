package com.example.movieflix.presentation.di.serie

import com.example.movieflix.presentation.ui.seriedetail.SerieDetailActivity
import com.example.movieflix.presentation.ui.serielist.SeriesListFragment
import com.example.movieflix.presentation.ui.seriesearch.SerieSearchFragment
import dagger.Subcomponent

@SerieScope
@Subcomponent(modules = [SerieModule::class])
interface SerieSubcomponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():SerieSubcomponent
    }

    fun inject(fragment: SeriesListFragment)
    fun inject(fragment: SerieSearchFragment)
    fun inject(activity: SerieDetailActivity)
}