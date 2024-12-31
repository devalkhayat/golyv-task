package com.golyv.features.home.ui.di

import com.golyv.features.home.domain.use_cases.GetCurrentWeatherInfoUseCase
import com.golyv.features.home.ui.navigation.HomeApi
import com.golyv.features.home.ui.navigation.HomeApiImpl
import com.golyv.features.home.ui.screens.home.HomeScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UiModule {

    @Provides
    fun provideDashboardApi(): HomeApi {
        return HomeApiImpl()
    }
    @Provides
    fun provideHomeScreenViewModel(getCurrentWeatherInfoUseCase: GetCurrentWeatherInfoUseCase): HomeScreenViewModel {
        return HomeScreenViewModel(getCurrentWeatherInfoUseCase)
    }
}