package com.example.mathTest.di

import com.example.mathTest.model.datasource.GameWebSocketDataSourceImpl
import com.example.mathTest.model.datasource.GameWebSocketDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WebSocketModule {

    @Binds
    @Singleton
    abstract fun bindGameWebSocket(
        impl: GameWebSocketDataSourceImpl
    ): GameWebSocketDataSource

    companion object {

        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
        }
    }
} 