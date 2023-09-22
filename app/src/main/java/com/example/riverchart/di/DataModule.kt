
package com.example.riverchart.di

import com.example.riverchart.data.DataRepository
import com.example.riverchart.data.DataRepositorySource
import com.example.riverchart.utils.MPChartHelp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Tells Dagger this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): DataRepositorySource
}
