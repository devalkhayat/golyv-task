package com.golyv.features.launch.ui.di

import com.golyv.features.launch.ui.navigation.LaunchApi
import com.golyv.features.launch.ui.navigation.LaunchApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object UiModule {

    @Provides
    fun provideLaunchApi(): LaunchApi {
        return LaunchApiImpl()
    }
}