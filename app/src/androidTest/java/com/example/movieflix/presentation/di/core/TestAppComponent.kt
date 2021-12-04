package com.example.movieflix.presentation.di.core

import android.content.Context
import com.example.movieflix.data.db.MovieDaoTest
import com.example.movieflix.data.db.SerieDaoTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [
    ApiServiceModule::class,
    TestDatabaseModule::class,
    LocalDataModule::class,
    RemoteDataModule::class,
    RepositoryModule::class
])
interface TestAppComponent : AppComponent{

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): TestAppComponent
    }

    fun inject(test:MovieDaoTest)
    fun inject(test:SerieDaoTest)
}