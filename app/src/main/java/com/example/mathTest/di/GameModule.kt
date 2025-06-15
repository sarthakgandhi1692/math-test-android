package com.example.mathTest.di

import com.example.mathTest.model.repository.GameRepository
import com.example.mathTest.model.repository.GameRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing dependencies related to the game.
 */
@Module
@InstallIn(SingletonComponent::class)
object GameModule {

    @Provides
    @Singleton
    fun provideGameRepository(impl: GameRepositoryImpl): GameRepository = impl

} 