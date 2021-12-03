package com.example.movieflix.presentation.di.home

import com.example.movieflix.presentation.ui.home.HomeFragment
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():HomeSubComponent
    }

    fun inject(fragment: HomeFragment)
}