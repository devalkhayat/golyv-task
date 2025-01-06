package com.golyv.weatherforecast.di

import com.golyv.features.home.ui.navigation.HomeApi
import com.golyv.features.launch.ui.navigation.LaunchApi
import com.golyv.features.memories.ui.navigation.MemoriesApi
import com.golyv.weatherforecast.navigation.NavigationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

  @Provides
    fun provideNavigationProvider(launchApi: LaunchApi,homeApi: HomeApi,memoriesApi: MemoriesApi
    ): NavigationProvider {
        return NavigationProvider(launchApi,homeApi,memoriesApi)
    }
}