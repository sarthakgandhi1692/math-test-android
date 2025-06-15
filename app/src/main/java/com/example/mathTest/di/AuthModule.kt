package com.example.mathTest.di

import com.example.mathTest.model.repository.AuthRepository
import com.example.mathTest.model.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing authentication-related dependencies.
 * This module is installed in the SingletonComponent, meaning that the provided dependencies will be singletons.
 */
@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

}