package com.example.movieflix.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.movieflix.presentation.ui.serielist.SerieListViewModel
import com.example.movieflix.presentation.ui.serielist.SeriesListFragment
import com.example.movieflix.presentation.ui.seriesearch.SerieSearchFragment
import com.example.movieflix.presentation.ui.seriesearch.SerieSearchViewModel

class TestSerieFragmentFactory(
    private val serieListViewModel: SerieListViewModel? = null,
    private val serieSearchViewModel: SerieSearchViewModel? = null
):FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            SeriesListFragment::class.java.name -> SeriesListFragment(serieListViewModel)
            SerieSearchFragment::class.java.name -> SerieSearchFragment(serieSearchViewModel)
            else -> super.instantiate(classLoader, className)
        }
    }
}
