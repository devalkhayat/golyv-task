package com.golyv.features.memories.ui.di

import com.golyv.features.memories.domain.use_cases.AddMemoryUseCase
import com.golyv.features.memories.domain.use_cases.GetAllMemoriesUseCase
import com.golyv.features.memories.ui.navigation.MemoriesApi
import com.golyv.features.memories.ui.navigation.MemoriesApiImpl
import com.golyv.features.memories.ui.screens.history.HistoryScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UiModule {

    @Provides
    fun provideDashboardApi(): MemoriesApi {
        return MemoriesApiImpl()
    }
    @Provides
    fun provideHistoryScreenViewModel(getAllMemoriesUseCase: GetAllMemoriesUseCase,addMemoryUseCase: AddMemoryUseCase): HistoryScreenViewModel {
        return HistoryScreenViewModel(getAllMemoriesUseCase,addMemoryUseCase)
    }
}