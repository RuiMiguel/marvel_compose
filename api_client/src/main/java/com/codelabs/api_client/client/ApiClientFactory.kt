package com.codelabs.api_client.client

import com.codelabs.api_client.interceptor.AuthInterceptor
import com.codelabs.api_client.interceptor.LoggingInterceptor
import com.codelabs.api_client.security.Security
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.logging.Logger

object ApiClientFactory {
    fun createAuthInterceptor(
        security: Security
    ): AuthInterceptor {
        return AuthInterceptor(security)
    }

    fun createLoggingInterceptor(
        logEnabled: Boolean
    ): LoggingInterceptor {
        return LoggingInterceptor(
            logEnabled = logEnabled,
            logger = Logger.getLogger(LoggingInterceptor::class.java.name),
        )
    }

    fun createOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun createRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    fun createApiClient(
        retrofit: Retrofit
    ): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}