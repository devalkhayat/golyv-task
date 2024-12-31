package com.golyv.features.home.data.di

import com.golyv.core.datasource.remote.dataproviders.WeatherDataProviders
import com.golyv.features.home.data.repo.WeatherRepoImpl
import com.golyv.features.home.domain.repo.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataLayerModule {

    @Provides
    fun provideWeatherRepo(weatherDataProviders: WeatherDataProviders):WeatherRepository= WeatherRepoImpl(weatherDataProviders)

}