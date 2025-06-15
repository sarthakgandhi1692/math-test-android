package com.example.mathTest.di

import com.example.mathTest.api.StringConverter
import com.example.mathTest.api.interceptor.AuthInterceptor
import com.example.mathTest.base.NullOnEmptyConverterFactory
import com.example.mathTest.di.base.ApplicationJsonAdapterFactory
import com.example.mathTest.di.qualifiers.BaseUrl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * NetworkModule class for dependency injection, provides retrofit, okHttpClient and other related network services
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesNullBodyConverter(): NullOnEmptyConverterFactory {
        return NullOnEmptyConverterFactory()
    }

    @Provides
    @Singleton
    fun providesMoshiConverterFactory(
        moshi: Moshi
    ): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun providesMoshiBuilder(): Moshi.Builder {
        return Moshi.Builder().add(ApplicationJsonAdapterFactory)
    }

    @Provides
    @Singleton
    fun providesMoshiInstance(
        moshiBuilder: Moshi.Builder
    ): Moshi {
        return moshiBuilder.build()
    }

    @Provides
    @Singleton
    fun providesStringConverter(): StringConverter {
        return StringConverter()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesCoroutineCallAdapterFactory(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory()
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun getOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        stringConverter: StringConverter,
        gsonConverterFactory: GsonConverterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
        moshiConverterFactory: MoshiConverterFactory,
        nullOnEmptyConverterFactory: NullOnEmptyConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(stringConverter)
            .addConverterFactory(moshiConverterFactory)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .build()
    }

}