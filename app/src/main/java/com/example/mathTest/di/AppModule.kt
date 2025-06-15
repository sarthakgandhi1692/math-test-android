package com.example.mathTest.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides dependencies for the application.
 * This module is installed in the [SingletonComponent], meaning that the provided dependencies
 * will have a singleton scope and will live as long as the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(
        application: Application
    ): Context {
        return application.applicationContext
    }
}