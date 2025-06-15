package com.example.mathTest.di

import com.example.mathTest.model.repository.GameRepository
import com.example.mathTest.model.repository.GameRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GameModule {

    @Provides
    @Singleton
    fun provideGameRepository(impl: GameRepositoryImpl): GameRepository = impl

} 