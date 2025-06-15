package com.example.mathTest.di

import com.example.mathTest.di.qualifiers.BaseUrl
import com.example.mathTest.di.qualifiers.SupabaseKey
import com.example.mathTest.di.qualifiers.SupabaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConstantsModule {

    @Provides
    @BaseUrl
    @Singleton
    fun provideBaseUrl(): String {
        return "https://math-game-backend-production-d2b1.up.railway.app/"
    }

    @Provides
    @SupabaseUrl
    @Singleton
    fun provideSupabaseUrl(): String {
        return "https://vyiddmxdfershfttxlsu.supabase.co"
    }

    @Provides
    @SupabaseKey
    @Singleton
    fun provideSupabaseKey(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZ5aWRkbXhkZmVyc2hmdHR4bHN1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDk3MzYyNDAsImV4cCI6MjA2NTMxMjI0MH0.F4bImtsk-DRn_Ya3SWlRr2z0s1XH06s_36exUf6s6IE"
    }


}