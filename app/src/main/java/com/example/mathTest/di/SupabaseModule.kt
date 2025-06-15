@file:OptIn(SupabaseInternal::class)

package com.example.mathTest.di

import com.example.mathTest.di.qualifiers.SupabaseKey
import com.example.mathTest.di.qualifiers.SupabaseUrl
import com.example.mathTest.model.datasource.SupabaseAuthDataSource
import com.example.mathTest.model.datasource.SupabaseAuthDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.ktor.utils.io.InternalAPI
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
@OptIn(InternalAPI::class)
class SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseAuthDataSource(
        impl: SupabaseAuthDataSourceImpl
    ): SupabaseAuthDataSource = impl

    companion object {

        @Provides
        @Singleton
        fun provideSupabaseClient(
            @SupabaseUrl supabaseUrl: String,
            @SupabaseKey supabaseKey: String,
        ): SupabaseClient {
            return createSupabaseClient(
                supabaseUrl = supabaseUrl,
                supabaseKey = supabaseKey,
            ) {
                requestTimeout = 30.seconds
                install(Auth)
            }
        }
    }
} 