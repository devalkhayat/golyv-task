package com.golyv.features.memories.data.di

import com.golyv.core.datasource.local.AppDatabase
import com.golyv.features.memories.data.repo.MemoriesRepoImpl
import com.golyv.features.memories.domain.repo.MemoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataLayerModule {

    @Provides
    fun provideMemoriesRepo(db: AppDatabase):MemoriesRepository=  MemoriesRepoImpl(db)


}