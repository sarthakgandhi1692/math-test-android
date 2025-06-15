package com.example.mathTest.di

import com.example.mathTest.di.qualifiers.DispatcherDefault
import com.example.mathTest.di.qualifiers.DispatcherIO
import com.example.mathTest.di.qualifiers.DispatcherMain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    @Singleton
    @DispatcherDefault
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Provides
    @Singleton
    @DispatcherIO
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    @DispatcherMain
    fun provideMainDispatcher() = Dispatchers.Main
}