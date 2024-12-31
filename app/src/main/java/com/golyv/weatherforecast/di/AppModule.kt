package com.golyv.weatherforecast.di

import com.golyv.features.home.ui.navigation.HomeApi
import com.golyv.weatherforecast.navigation.NavigationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideNavigationProvider(homeApi: HomeApi,
    ): NavigationProvider {
        return NavigationProvider(homeApi)
    }
}