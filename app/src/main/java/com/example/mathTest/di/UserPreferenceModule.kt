package com.example.mathTest.di

import com.example.mathTest.model.datasource.UserPreferencesDataSource
import com.example.mathTest.model.datasource.UserPreferencesDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing user preference related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class UserPreferenceModule {

    @Provides
    @Singleton
    fun provideUserPreferencesDataSource(
        impl: UserPreferencesDataSourceImpl
    ): UserPreferencesDataSource = impl

}