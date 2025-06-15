package com.example.mathTest.di

import com.example.mathTest.api.LeaderboardApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun providesLeaderboardApi(
        retrofit: Retrofit
    ): LeaderboardApi {
        return retrofit.create(LeaderboardApi::class.java)
    }
}