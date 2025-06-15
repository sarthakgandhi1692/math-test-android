package com.example.mathTest.di

import com.example.mathTest.model.datasource.LeaderBoardDataSource
import com.example.mathTest.model.datasource.LeaderBoardDataSourceImpl
import com.example.mathTest.model.repository.LeaderboardRepository
import com.example.mathTest.model.repository.LeaderboardRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies related to the leaderboard feature.
 */
@Module
@InstallIn(SingletonComponent::class)
class LeaderBoardModule {

    @Provides
    @Singleton
    fun provideLeaderBoardRepository(impl: LeaderboardRepositoryImpl): LeaderboardRepository = impl

    @Provides
    @Singleton
    fun provideLeaderBoardDataSource(impl: LeaderBoardDataSourceImpl): LeaderBoardDataSource = impl
}