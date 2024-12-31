package com.golyv.core.datasource.local.di

import android.content.Context
import com.golyv.core.datasource.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context):AppDatabase{
        return AppDatabase.getDatabase(appContext)
    }
}