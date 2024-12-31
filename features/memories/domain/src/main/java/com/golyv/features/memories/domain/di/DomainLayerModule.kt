package com.golyv.features.memories.domain.di

import com.golyv.features.memories.domain.repo.MemoriesRepository
import com.golyv.features.memories.domain.use_cases.AddMemoryUseCase
import com.golyv.features.memories.domain.use_cases.GetAllMemoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainLayerModule {

    @Provides
    fun provideAddMemoryUseCase(memoriesRepository: MemoriesRepository):AddMemoryUseCase{
        return AddMemoryUseCase(memoriesRepository)
    }

    @Provides
    fun provideGetAllMemoriesUseCase(memoriesRepository: MemoriesRepository):GetAllMemoriesUseCase{
        return GetAllMemoriesUseCase(memoriesRepository)
    }
}