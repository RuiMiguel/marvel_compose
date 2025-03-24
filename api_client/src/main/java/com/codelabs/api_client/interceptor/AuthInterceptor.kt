package com.codelabs.api_client.interceptor

import com.codelabs.api_client.exception.ApiException.AuthenticationException
import com.codelabs.api_client.security.Security
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(private val security: Security) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        return try {
            val (hash, timestamp) = security.hashTimestamp()
            val publicKey = security.publicKey

            val queryParameters = mutableMapOf(
                "ts" to timestamp,
                "hash" to hash,
                "apikey" to publicKey
            ).toMap()

            val url = originalRequest.url.newBuilder().apply {
                queryParameters.forEach {
                    val (key, value) = it
                    addQueryParameter(key, value)
                }
            }.build()

            val builder = originalRequest.newBuilder().url(url)

            val request = builder.method(originalRequest.method, originalRequest.body).build()
            chain.proceed(request)
        } catch (exception: IOException) {
            throw AuthenticationException(exception.message)
        }
    }
}