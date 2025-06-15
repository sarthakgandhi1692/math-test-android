package com.example.mathTest.di

import com.example.mathTest.BuildConfig
import com.example.mathTest.di.qualifiers.BaseUrl
import com.example.mathTest.di.qualifiers.SupabaseKey
import com.example.mathTest.di.qualifiers.SupabaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides constant values.
 */
@Module
@InstallIn(SingletonComponent::class)
class ConstantsModule {

    @Provides
    @BaseUrl
    @Singleton
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @SupabaseUrl
    @Singleton
    fun provideSupabaseUrl(): String {
        return BuildConfig.SUPABASE_URL
    }

    @Provides
    @SupabaseKey
    @Singleton
    fun provideSupabaseKey(): String {
        return BuildConfig.SUPABASE_KEY
    }


}