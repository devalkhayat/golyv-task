package com.golyv.features.home.domain.di

import com.golyv.features.home.domain.repo.WeatherRepository
import com.golyv.features.home.domain.use_cases.GetCurrentWeatherInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainLayerModule {

    @Provides
    fun provideGetCurrentWeatherInfoUseCase(weatherRepository: WeatherRepository):GetCurrentWeatherInfoUseCase= GetCurrentWeatherInfoUseCase(weatherRepository)

}