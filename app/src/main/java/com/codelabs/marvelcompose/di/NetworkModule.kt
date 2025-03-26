package com.codelabs.marvelcompose.di

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.client.ApiClientFactory
import com.codelabs.api_client.interceptor.AuthInterceptor
import com.codelabs.api_client.interceptor.LoggingInterceptor
import com.codelabs.api_client.security.Security
import com.codelabs.secure_storage.SecureStorage
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://gateway.marvel.com/v1/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideSecurity(): Security {
        return Security()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        security: Security,
        secureStorage: SecureStorage,
    ): AuthInterceptor {
        return ApiClientFactory.createAuthInterceptor(
            security = security,
            secureStorage = secureStorage
        )
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): LoggingInterceptor {
        return ApiClientFactory.createLoggingInterceptor(logEnabled = true)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        return ApiClientFactory.createOkHttpClient(authInterceptor, loggingInterceptor)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return ApiClientFactory.createRetrofit(BASE_URL, okHttpClient, moshi)
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return ApiClientFactory.createApiClient(retrofit)
    }
}